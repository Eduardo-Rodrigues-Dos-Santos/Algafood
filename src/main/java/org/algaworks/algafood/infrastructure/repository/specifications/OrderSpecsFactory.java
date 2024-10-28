package org.algaworks.algafood.infrastructure.repository.specifications;

import jakarta.persistence.criteria.*;
import org.algaworks.algafood.domain.models.Order;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecsFactory {

    public static Specification<Order> ordersByRestaurant(Long restaurantId) {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            root.join("deliveryAddress").fetch("city").fetch("state");
            root.fetch("paymentMethod");
            root.fetch("client");
            root.fetch("restaurant").fetch("kitchen");
            return builder.equal(root.get("restaurant").get("id"), restaurantId);
        };
    }

    public static Specification<Order> ordersByClient(Long clientId) {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            root.join("deliveryAddress").fetch("city").fetch("state");
            root.fetch("paymentMethod");
            root.fetch("client");
            root.fetch("restaurant").fetch("kitchen");
            return builder.equal(root.get("client").get("id"), clientId);
        };
    }
}
