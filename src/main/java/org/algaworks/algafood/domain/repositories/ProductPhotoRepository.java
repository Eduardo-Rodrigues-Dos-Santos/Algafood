package org.algaworks.algafood.domain.repositories;

import org.algaworks.algafood.domain.models.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {

    @Query("select ph from ProductPhoto ph join ph.product p join p.restaurant r " +
            "where p.id = :productId and r.code = :restaurantCode")
    List<ProductPhoto> findAllPhotosByProduct(@Param("productId") Long productId, @Param("restaurantCode") String restaurantCode);

    @Query("select ph from ProductPhoto ph join ph.product p join p.restaurant r where ph.code = :photoCode and " +
            "p.id = :productId and r.code = :restaurantCode")
    Optional<ProductPhoto> findPhotoByCode(@Param("photoCode") String photoCode,
                                           @Param("productId") Long productId,
                                           @Param("restaurantCode") String restaurantCode);
}
