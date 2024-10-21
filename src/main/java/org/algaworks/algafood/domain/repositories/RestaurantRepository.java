package org.algaworks.algafood.domain.repositories;

import org.algaworks.algafood.domain.models.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>,
        CustomRestaurantRepository, JpaSpecificationExecutor<Restaurant> {

    @Override
    @Query("select r from Restaurant r left join fetch r.kitchen")
    Page<Restaurant> findAll(Pageable pageable);

    @Override
    @Query("select r from Restaurant r left join fetch r.address.city " +
            "left join fetch r.address.city.state left join fetch r.kitchen where r.id = :id")
    Optional<Restaurant> findById(@Param("id") Long id);
}
