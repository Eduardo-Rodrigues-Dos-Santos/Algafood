package org.algaworks.algafood.domain.exceptions;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        this(String.format("There is no user registered with id %d", id));
    }
}
