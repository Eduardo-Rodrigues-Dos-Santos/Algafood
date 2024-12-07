package org.algaworks.algafood.domain.repositories;

import org.algaworks.algafood.domain.models.Product;
import org.algaworks.algafood.domain.models.ProductPhoto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CustomJpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("select p from Product p left join fetch p.restaurant r left join fetch r.kitchen where p.id = :productId")
    Optional<Product> findProductByRestaurant(@Param("productId") Long productId);
}
