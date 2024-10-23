package org.algaworks.algafood.domain.repositories;

import org.algaworks.algafood.domain.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long> {

    @Query("select o from Order o left join fetch o.deliveryAddress.city c left join fetch c.state " +
            "left join fetch o.paymentMethod left join fetch o.client left join fetch o.restaurant r " +
            "left join fetch r.kitchen left join fetch o.items i left join fetch i.product where o.id = :id")
    Optional<Order> findById(@Param("id") Long id);

    @Query("select o from Order o left join fetch o.client left join fetch o.restaurant r left join fetch r.kitchen " +
            "left join fetch o.paymentMethod")
    Page<Order> findAll(Pageable pageable);
}
