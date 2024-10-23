package org.algaworks.algafood.domain.exceptions;

public class OrderNotFoundException extends EntityNotFoundException {

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(Long id) {
        this(String.format("There is no registered order with id %d", id));
    }
}
