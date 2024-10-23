package org.algaworks.algafood.api.exceptionhandler;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.AllArgsConstructor;
import org.algaworks.algafood.domain.exceptions.BusinessException;
import org.algaworks.algafood.domain.exceptions.EntityInUseException;
import org.algaworks.algafood.domain.exceptions.EntityNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.algaworks.algafood.api.exceptionhandler.ProblemType.*;

@ControllerAdvice
@AllArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handlerEntityNotFoundException(EntityNotFoundException ex, WebRequest webRequest) {

        Problem problem = createProblemBuilder(HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND, ex.getMessage()).build();
        return handleExceptionInternal(ex, problem, HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<Object> handlerEntityInUseException(EntityInUseException ex, WebRequest request) {

        Problem problem = createProblemBuilder(HttpStatus.CONFLICT, ENTITY_IN_USE, ex.getMessage()).build();
        return handleExceptionInternal(ex, problem, HttpHeaders.EMPTY, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handlerBusinessException(BusinessException ex, WebRequest request) {

        Problem problem = createProblemBuilder(HttpStatus.BAD_REQUEST, BUSINESS_ERROR, ex.getMessage()).build();
        return handleExceptionInternal(ex, problem, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handlerMethodArgumentTypeMisMatchException(MethodArgumentTypeMismatchException ex,
                                                                             WebRequest request) {
        String detail = String.format(ProblemMessage.INVALID_URL_PARAMETER.getMessage(), ex.getPropertyName(), ex.getValue());
        Problem problem = createProblemBuilder(HttpStatus.BAD_REQUEST, INVALID_PARAMETER, detail).build();
        return handleExceptionInternal(ex, problem, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handlerAccessDinedException(AccessDeniedException ex, WebRequest request) {
        Problem problem = createProblemBuilder(HttpStatus.FORBIDDEN, FORBIDDEN, ProblemMessage.FORBIDDEN.getMessage()).build();
        return handleExceptionInternal(ex, problem, HttpHeaders.EMPTY, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) {

        Problem problem = createProblemBuilder(HttpStatus.INTERNAL_SERVER_ERROR, SYSTEM_ERROR,
                ProblemMessage.SYSTEM_ERROR.getMessage()).build();
        ex.printStackTrace();
        return handleExceptionInternal(ex, problem, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        List<InvalidField> invalidFields = ex.getFieldErrors().stream().map(field -> {
            String messageError = messageSource.getMessage(field, Locale.getDefault());
            String path = String.join(".", field.getObjectName(), field.getField());
            return new InvalidField(path, messageError);
        }).toList();

        Problem problem = createProblemBuilder(HttpStatus.valueOf(status.value()), INVALID_DATA,
                ProblemMessage.INVALID_DATA.getMessage()).invalidFields(invalidFields).build();
        return handleExceptionInternal(ex, problem, headers, HttpStatus.valueOf(status.value()), request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatusCode status, WebRequest request) {
        String detail = String.format(ProblemMessage.NON_EXISTENT_RESOURCE.getMessage(), ex.getRequestURL());
        Problem problem = createProblemBuilder(HttpStatus.valueOf(status.value()), RESOURCE_NOT_FOUND, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ex.getRootCause();
        if (rootCause instanceof InvalidFormatException invalidFormatException) {
            return handleInvalidFormatException(invalidFormatException, headers, status, request);
        }
        Problem problem = createProblemBuilder(HttpStatus.valueOf(status.value()), INCOMPREHENSIBLE_MESSAGE,
                ProblemMessage.INVALID_BODY.getMessage()).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
                                                                HttpStatusCode statusCode, WebRequest request) {
        String field = ex.getPath().stream().map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
        String detail = String.format(ProblemMessage.INVALID_TYPE.getMessage(), field, ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(HttpStatus.valueOf(statusCode.value()), INCOMPREHENSIBLE_MESSAGE, detail).build();
        return handleExceptionInternal(ex, problem, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode statusCode, WebRequest request) {
        if (body instanceof String detail) {
            body = Problem.builder()
                    .status(statusCode.value())
                    .title(HttpStatus.valueOf(statusCode.value()).getReasonPhrase())
                    .detail(detail)
                    .timestamp(OffsetDateTime.now()).build();
        } else if (body == null) {
            body = Problem.builder()
                    .status(statusCode.value())
                    .title(HttpStatus.valueOf(statusCode.value()).getReasonPhrase())
                    .detail(ex.getLocalizedMessage())
                    .timestamp(OffsetDateTime.now()).build();
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType type, String detail) {
        return Problem.builder()
                .status(status.value())
                .type(type.getUri())
                .title(type.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }
}
