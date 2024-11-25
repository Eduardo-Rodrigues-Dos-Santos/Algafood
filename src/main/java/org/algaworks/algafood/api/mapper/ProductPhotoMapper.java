package org.algaworks.algafood.api.mapper;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.models.ProductPhotoModel;
import org.algaworks.algafood.api.models.input.ProductPhotoInput;
import org.algaworks.algafood.domain.models.ProductPhoto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductPhotoMapper {

    private ModelMapper modelMapper;

    public ProductPhoto toProductPhoto(ProductPhotoInput productPhotoInput) {
        return modelMapper.map(productPhotoInput, ProductPhoto.class);
    }

    public ProductPhotoModel productPhotoModel(ProductPhoto productPhoto) {
        return modelMapper.map(productPhoto, ProductPhotoModel.class);
    }
}
