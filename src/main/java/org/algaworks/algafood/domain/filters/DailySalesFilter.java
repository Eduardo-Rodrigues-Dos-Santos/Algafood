package org.algaworks.algafood.domain.filters;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DailySalesFilter {

    private LocalDate startDate;
    private LocalDate endDate;
}
