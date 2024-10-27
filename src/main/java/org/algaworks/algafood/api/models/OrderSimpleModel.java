package org.algaworks.algafood.api.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class OrderSimpleModel {

    private String code;
    private UserModel client;
    private String restaurantName;
    private PaymentMethodModel paymentMethod;
    private String orderStatus;
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal total;
    private OffsetDateTime creationDate;
}
