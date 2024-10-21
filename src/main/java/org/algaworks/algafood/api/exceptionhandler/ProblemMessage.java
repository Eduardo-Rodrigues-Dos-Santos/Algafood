package org.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemMessage {

    INVALID_BODY("The request body is invalid, check syntax error."),
    INVALID_TYPE("The %s field was assigned an invalid type, please enter a value compatible with %s."),
    INVALID_URL_PARAMETER("The URL parameter %s received the value '%s' which is invalid, please enter a value compatible with Long."),
    NON_EXISTENT_RESOURCE("Resource %s non-existent."),
    SYSTEM_ERROR("An unexpected internal error has occurred. Try again and if the problem persists, contact your system administrator."),
    INVALID_DATA("One or more fields are invalid, fill them in correctly and try again.");

    private String message;

    ProblemMessage(String message) {
        this.message = message;
    }
}
