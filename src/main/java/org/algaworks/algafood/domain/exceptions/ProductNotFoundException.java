package org.algaworks.algafood.domain.exceptions;

public class ProductNotFoundException extends EntityNotFoundException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long id) {
        this(String.format("There is no product registered with id %d.", id));
    }
}
