package org.algaworks.algafood.domain.services;

import org.algaworks.algafood.api.models.DailySales;
import org.algaworks.algafood.domain.filters.DailySalesFilter;

import java.util.List;

public interface SalesStatistics {

    public List<DailySales> dailySalesStatistics(String restaurantCode, String offSet, DailySalesFilter dailySalesFilter);
}
