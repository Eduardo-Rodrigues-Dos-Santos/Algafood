package org.algaworks.algafood.domain.exceptions;

public class KitchenNotFoundException extends EntityNotFoundException {

    protected KitchenNotFoundException(String message) {
        super(message);
    }

    public KitchenNotFoundException(Long id) {
        this(String.format("There is no kitchen registered with id %d", id));
    }
}
