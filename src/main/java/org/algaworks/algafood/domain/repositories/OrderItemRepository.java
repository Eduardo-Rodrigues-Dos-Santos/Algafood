package org.algaworks.algafood.domain.repositories;

import org.algaworks.algafood.domain.models.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends CustomJpaRepository<OrderItem, Long> {
}
