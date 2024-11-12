package org.algaworks.algafood.api.controllers;

import lombok.AllArgsConstructor;
import org.algaworks.algafood.api.models.DailySales;
import org.algaworks.algafood.core.security.security_annotations.CheckSecurity;
import org.algaworks.algafood.domain.exceptions.BusinessException;
import org.algaworks.algafood.domain.exceptions.RestaurantNotFoundException;
import org.algaworks.algafood.domain.filters.DailySalesFilter;
import org.algaworks.algafood.infrastructure.service.SalesStatisticsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics/restaurants/{restaurantCode}")
@AllArgsConstructor
public class SalesStatisticsController {

    private SalesStatisticsImpl salesStatistics;

    @CheckSecurity.Restaurant.StatisticsConsult
    @GetMapping("/daily-sales")
    public ResponseEntity<Page<DailySales>> dailySales(@PathVariable String restaurantCode,
                                                       DailySalesFilter dailySalesFilter, @PageableDefault Pageable pageable,
                                                       @RequestParam(required = false, defaultValue = "+00:00") String offSet) {
        try {
            List<DailySales> dailySales = salesStatistics.dailySalesStatistics(restaurantCode, offSet, dailySalesFilter);
            return ResponseEntity.ok(new PageImpl<DailySales>(dailySales, pageable, dailySales.size()));
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
