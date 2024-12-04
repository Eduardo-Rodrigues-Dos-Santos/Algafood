package org.algaworks.algafood.api.models.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.algaworks.algafood.core.validations.FileSize;
import org.algaworks.algafood.core.validations.FileType;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductPhotoInput {

    @NotBlank
    private String fileName;

    @NotBlank
    private String description;

    @NotNull
    @FileType(allowed = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @FileSize(sizeAllowed = "3910KB")
    private MultipartFile photo;
}
