package org.algaworks.algafood.domain.exceptions;

public class CityNotFoundException extends EntityNotFoundException {

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(Long id) {
        this(String.format("There is no city registered with id %d", id));
    }
}
