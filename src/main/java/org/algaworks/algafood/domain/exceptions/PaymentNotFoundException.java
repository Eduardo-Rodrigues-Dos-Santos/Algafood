package org.algaworks.algafood.domain.exceptions;

public class PaymentNotFoundException extends EntityNotFoundException {

    protected PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(Long id) {
        super(String.format("There is no payment method registered with id %d", id));
    }
}
