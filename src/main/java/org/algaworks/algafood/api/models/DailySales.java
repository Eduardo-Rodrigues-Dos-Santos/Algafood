package org.algaworks.algafood.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class DailySales {

    private LocalDate date;
    private Long amountSales;
    private BigDecimal totalValue;
}
