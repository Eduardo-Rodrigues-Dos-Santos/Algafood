package org.algaworks.algafood.domain.exceptions;

public class OrderItemNotFoundException extends EntityNotFoundException {

    public OrderItemNotFoundException(String message) {
        super(message);
    }

    public OrderItemNotFoundException(Long id) {
        this(String.format("There is no order item registered with id %d", id));
    }
}
