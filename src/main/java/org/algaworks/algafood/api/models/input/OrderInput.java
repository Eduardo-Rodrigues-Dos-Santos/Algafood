package org.algaworks.algafood.api.models.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderInput {

    @NotEmpty
    private String restaurantCode;

    @NotNull
    @Positive
    private Long paymentMethodId;

    @Valid
    @NotNull
    private AddressInput deliveryAddress;

    @Valid
    @NotNull
    private List<OrderItemInput> items;
}
