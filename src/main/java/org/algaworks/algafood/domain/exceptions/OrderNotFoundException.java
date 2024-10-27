package org.algaworks.algafood.domain.exceptions;

public class OrderNotFoundException extends EntityNotFoundException {

    public OrderNotFoundException(String code) {
        super(String.format("There is no registered order with id %s", code));
    }

    public OrderNotFoundException(Long id) {
        this(String.format("There is no registered order with id %d", id));
    }
}
