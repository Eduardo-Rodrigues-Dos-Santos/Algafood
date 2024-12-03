package org.algaworks.algafood.domain.exceptions;

public class ProductPhotoNotFoundException extends EntityNotFoundException{

    protected ProductPhotoNotFoundException(String message) {
        super(message);
    }

    public ProductPhotoNotFoundException(Long id){
        this(String.format("There is no photo registered with id %d", id));
    }
}
