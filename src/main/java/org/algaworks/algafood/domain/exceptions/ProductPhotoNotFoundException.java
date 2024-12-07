package org.algaworks.algafood.domain.exceptions;

public class ProductPhotoNotFoundException extends EntityNotFoundException {

    public ProductPhotoNotFoundException(String photoCode) {
        super(String.format("There is no photo registered with code %s", photoCode));
    }
}
