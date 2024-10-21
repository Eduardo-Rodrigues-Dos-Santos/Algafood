package org.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidField {

    private String field;
    private String message;

    public InvalidField(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
