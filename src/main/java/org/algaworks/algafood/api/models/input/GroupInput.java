package org.algaworks.algafood.api.models.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupInput {

    @NotBlank
    private String name;
}
