package org.algaworks.algafood.api.models.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateInput {

    @NotBlank
    private String name;

    public StateInput() {
    }

    @Builder
    public StateInput(String name) {
        this.name = name;
    }
}
