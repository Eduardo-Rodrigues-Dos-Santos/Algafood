package org.algaworks.algafood.infrastructure.repository.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.algaworks.algafood.domain.models.Order;
import org.algaworks.algafood.domain.filters.OrderFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class OrderSpecsFactory {

    public static Specification<Order> ordersByRestaurant(Long restaurantId, OrderFilter orderFilter) {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            root.join("deliveryAddress").fetch("city").fetch("state");
            root.fetch("paymentMethod");
            root.fetch("client");
            root.fetch("restaurant").fetch("kitchen");
            ArrayList<Predicate> predicates = filterPredicates(orderFilter, root, builder);
            predicates.add(builder.equal(root.get("restaurant").get("id"), restaurantId));
            return builder.and(predicates.toArray(new Predicate[0]));
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

    private static ArrayList<Predicate> filterPredicates(OrderFilter orderFilter, Root<Order> root, CriteriaBuilder builder) {
        ArrayList<Predicate> predicates = new ArrayList<>();
        if (orderFilter.getClientId() != null) {
            predicates.add(builder.equal(root.get("client").get("id"), orderFilter.getClientId()));
        }
        if (orderFilter.getStartDate() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), orderFilter.getStartDate()));
        }
        if (orderFilter.getEndDate() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("deliveryDate"), orderFilter.getEndDate()));
        }
        if (orderFilter.getPaymentMethodId() != null){
            predicates.add(builder.equal(root.get("paymentMethod").get("id"), orderFilter.getPaymentMethodId()));
        }
        return predicates;
    }
}
