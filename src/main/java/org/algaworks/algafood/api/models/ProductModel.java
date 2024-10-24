package org.algaworks.algafood.api.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductModel {

    private Long id;
    private String name;
    private String description;
    private BigDecimal value;
    private Boolean active;
}
