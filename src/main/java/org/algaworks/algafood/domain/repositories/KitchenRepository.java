package org.algaworks.algafood.domain.repositories;

import org.algaworks.algafood.domain.models.Kitchen;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenRepository extends CustomJpaRepository<Kitchen, Long> {
}
