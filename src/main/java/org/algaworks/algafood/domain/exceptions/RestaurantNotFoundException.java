package org.algaworks.algafood.domain.exceptions;

public class RestaurantNotFoundException extends EntityNotFoundException {

    protected RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(Long id) {
        this(String.format("There is no restaurant registered with id %d", id));
    }
}
