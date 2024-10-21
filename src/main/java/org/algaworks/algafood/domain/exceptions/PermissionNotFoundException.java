package org.algaworks.algafood.domain.exceptions;

public class PermissionNotFoundException extends EntityNotFoundException {

    public PermissionNotFoundException(String message) {
        super(message);
    }

    public PermissionNotFoundException(Long id) {
        this(String.format("There is no registered permission with id %d", id));
    }
}
