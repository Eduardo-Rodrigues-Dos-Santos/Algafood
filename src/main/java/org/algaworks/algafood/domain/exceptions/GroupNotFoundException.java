package org.algaworks.algafood.domain.exceptions;

public class GroupNotFoundException extends EntityNotFoundException {

    public GroupNotFoundException(String message) {
        super(message);
    }

    public GroupNotFoundException(Long id) {
        this(String.format("There is no group registered with id %d", id));
    }
}
