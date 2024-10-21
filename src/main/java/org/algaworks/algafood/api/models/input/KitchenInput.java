package org.algaworks.algafood.api.models.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KitchenInput {

    @NotBlank
    private String name;

    public KitchenInput() {
    }

    @Builder
    public KitchenInput(String name) {
        this.name = name;
    }
}
