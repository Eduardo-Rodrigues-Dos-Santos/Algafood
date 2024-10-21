package org.algaworks.algafood.api.models.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityInput {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Long stateId;

    public CityInput() {
    }

    @Builder
    public CityInput(String name, Long stateId) {
        this.name = name;
        this.stateId = stateId;
    }
}
