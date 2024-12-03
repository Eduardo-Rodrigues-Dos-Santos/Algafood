package org.algaworks.algafood.domain.repositories;

import org.algaworks.algafood.domain.models.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {

    @Query("select ph from ProductPhoto ph left join ph.product p where ph.id = :photoId and p.id = :productId")
    Optional<ProductPhoto> findPhotoByProduct(@Param("photoId") Long photoId, @Param("productId") Long productId);
}
