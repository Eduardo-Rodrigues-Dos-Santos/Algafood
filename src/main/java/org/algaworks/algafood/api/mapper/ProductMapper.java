package org.algaworks.algafood.api.mapper;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.models.ProductModel;
import org.algaworks.algafood.api.models.input.ProductInput;
import org.algaworks.algafood.domain.models.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMapper {

    private ModelMapper modelMapper;

    public Product toProduct(ProductInput productInput) {
        return modelMapper.map(productInput, Product.class);
    }

    public ProductModel toProductModel(Product product) {
        return modelMapper.map(product, ProductModel.class);
    }

    public void copyToDomainObject(ProductInput productInput, Product product) {
        modelMapper.map(productInput, product);
    }
}
