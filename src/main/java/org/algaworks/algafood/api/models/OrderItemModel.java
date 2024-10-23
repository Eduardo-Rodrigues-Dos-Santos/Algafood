package org.algaworks.algafood.api.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemModel {

    private Long id;
    private String productName;
    private Integer quantity;
    private BigDecimal unitValue;
    private BigDecimal totalValue;
    private String observation;
}
