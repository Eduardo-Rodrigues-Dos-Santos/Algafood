package org.algaworks.algafood.infrastructure.repository.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.algaworks.algafood.domain.models.City;
import org.springframework.data.jpa.domain.Specification;

public class CitySpecsFactory {

    private CitySpecsFactory() {
    }

    public static Specification<City> citiesByState(Long stateId) {
        return (Root<City> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Path<Object> state = root.get("state");
            return builder.equal(state.get("id"), stateId);
        };
    }
}
