package org.algaworks.algafood.domain.repositories;

import org.algaworks.algafood.domain.models.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {

}
