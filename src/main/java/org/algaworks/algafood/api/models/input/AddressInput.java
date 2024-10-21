package org.algaworks.algafood.api.models.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressInput {

    @NotNull
    @Positive
    private Long cityId;

    @NotNull
    @Positive
    private String postalCode;

    @NotNull
    @Positive
    private String number;

    @NotBlank
    private String complement;

    @NotBlank
    private String district;

    public AddressInput() {
    }

    @Builder
    public AddressInput(Long cityId, String postalCode, String number, String complement, String district) {
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.number = number;
        this.complement = complement;
        this.district = district;
    }
}
