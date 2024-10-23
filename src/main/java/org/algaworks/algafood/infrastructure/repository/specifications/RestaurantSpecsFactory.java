package org.algaworks.algafood.infrastructure.repository.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.algaworks.algafood.domain.models.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecsFactory {

    private RestaurantSpecsFactory() {
    }

    public static Specification<Restaurant> withFreeDelivery() {
        return (Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            root.fetch("kitchen");
            return builder.equal(root.get("fee"), BigDecimal.ZERO);
        };
    }

    public static Specification<Restaurant> byLikeName(String name) {
        return (Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            root.fetch("kitchen");
            return builder.like(root.get("name".toLowerCase()), "%" + name.toLowerCase() + "%");
        };
    }

}

