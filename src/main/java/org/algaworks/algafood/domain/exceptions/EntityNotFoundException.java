package org.algaworks.algafood.domain.exceptions;

public abstract class EntityNotFoundException extends RuntimeException {

    protected EntityNotFoundException(String message) {
        super(message);
    }
}
