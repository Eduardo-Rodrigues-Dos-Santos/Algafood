package org.algaworks.algafood.domain.exceptions;

public class StateNotFoundException extends EntityNotFoundException {

    public StateNotFoundException(String message) {
        super(message);
    }

    public StateNotFoundException(Long id) {
        this(String.format("There is no state registered with id %d", id));
    }
}