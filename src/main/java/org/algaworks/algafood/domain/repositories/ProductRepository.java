package org.algaworks.algafood.domain.repositories;

import org.algaworks.algafood.domain.models.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CustomJpaRepository<Product, Long> {

    @Query("select p from Product p left join fetch p.restaurant r where p.restaurant.id = :restaurantId and p.id = :productId")
    Optional<Product> findByRestaurant(@Param("restaurantId") Long restaurantId, @Param("productId") Long productId);
}
