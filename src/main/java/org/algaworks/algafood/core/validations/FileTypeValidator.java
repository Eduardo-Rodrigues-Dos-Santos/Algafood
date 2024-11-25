package org.algaworks.algafood.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileTypeValidator implements ConstraintValidator<FileType, MultipartFile> {

    private List<String> allowed;

    @Override
    public void initialize(FileType constraintAnnotation) {
        this.allowed = List.of(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null || allowed.contains(value.getContentType());
    }
}
