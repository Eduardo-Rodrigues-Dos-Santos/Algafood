package org.algaworks.algafood.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private DataSize sizeAllowed;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        sizeAllowed = DataSize.parse(constraintAnnotation.sizeAllowed());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null || value.getSize() <= sizeAllowed.toBytes();
    }
}
