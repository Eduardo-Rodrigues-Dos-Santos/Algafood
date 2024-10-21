package org.algaworks.algafood.api.models.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantInput {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Long kitchenId;

    @PositiveOrZero
    private BigDecimal shippingFee;

    @Valid
    @NotNull
    private AddressInput address;

    public RestaurantInput() {
    }

    @Builder
    public RestaurantInput(String name, Long kitchenId, AddressInput address) {
        this.name = name;
        this.kitchenId = kitchenId;
        this.address = address;
    }
}
