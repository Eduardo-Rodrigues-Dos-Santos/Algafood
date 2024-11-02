package org.algaworks.algafood.infrastructure.repository.specifications;

import jakarta.persistence.criteria.*;
import org.algaworks.algafood.domain.models.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecsFactory {

    public static Specification<Product> activesByRestaurant(String restaurantCode) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            root.fetch("restaurant").fetch("kitchen");
            Path<Object> code = root.get("restaurant").get("code");
            Predicate active = builder.equal(root.get("active"), true);
            Predicate restaurantFilter = builder.equal(code, restaurantCode);
            return builder.and(restaurantFilter, active);
        };
    }

    public static Specification<Product> restaurantCode(String restaurantCode) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            root.fetch("restaurant").fetch("kitchen");
            Path<Object> code = root.get("restaurant").get("code");
            return builder.equal(code, restaurantCode);
        };
    }
}
