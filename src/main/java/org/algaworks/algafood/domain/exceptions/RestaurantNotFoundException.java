package org.algaworks.algafood.domain.exceptions;

public class RestaurantNotFoundException extends EntityNotFoundException {

    public RestaurantNotFoundException(String code) {
        super(String.format("There is no restaurant registered with code %s", code));
    }

    public RestaurantNotFoundException(Long id) {
        this(String.format("There is no restaurant registered with code %d", id));
    }
}
