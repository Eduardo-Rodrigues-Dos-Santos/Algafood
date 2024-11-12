package org.algaworks.algafood.infrastructure.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.algaworks.algafood.api.models.DailySales;
import org.algaworks.algafood.domain.filters.DailySalesFilter;
import org.algaworks.algafood.domain.models.Order;
import org.algaworks.algafood.domain.models.OrderStatus;
import org.algaworks.algafood.domain.services.RestaurantService;
import org.algaworks.algafood.domain.services.SalesStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalesStatisticsImpl implements SalesStatistics {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public List<DailySales> dailySalesStatistics(String restaurantCode, String offSet, DailySalesFilter dailySalesFilter) {

        restaurantService.findByCode(restaurantCode);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DailySales> query = builder.createQuery(DailySales.class);
        Root<Order> root = query.from(Order.class);

        Expression<LocalDate> convertTz = builder.function("convert_tz", LocalDate.class,
                root.get("creationDate"), builder.literal("+00:00"), builder.literal(offSet));
        Expression<LocalDate> date = builder.function("date", LocalDate.class, convertTz);

        CriteriaQuery<DailySales> multiselect = query.multiselect(date, builder.count(root.get("id")),
                builder.sum(root.get("totalValue")));

        List<Predicate> predicates = filterPredicates(dailySalesFilter, root, builder);
        predicates.add(builder.equal(root.get("restaurant").get("code"), restaurantCode));

        multiselect.where(predicates.toArray(new Predicate[0]));
        multiselect.groupBy(date);
        return entityManager.createQuery(multiselect).getResultList();
    }

    private List<Predicate> filterPredicates(DailySalesFilter dailySalesFilter, Root<Order> root,
                                             CriteriaBuilder builder) {

        Expression<LocalDate> date = builder.function("date", LocalDate.class, root.get("creationDate"));
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (dailySalesFilter.getStartDate() != null) {
            Predicate startDate = builder.greaterThanOrEqualTo(date, dailySalesFilter.getStartDate());
            predicates.add(startDate);
        }

        if (dailySalesFilter.getEndDate() != null) {
            Predicate endDate = builder.lessThanOrEqualTo(date, dailySalesFilter.getEndDate());
            predicates.add(endDate);
        }

        predicates.add(root.get("orderStatus").in(OrderStatus.CONFIRMED, OrderStatus.DELIVERED));
        return predicates;
    }
}
