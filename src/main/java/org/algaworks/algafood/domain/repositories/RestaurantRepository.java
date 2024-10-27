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

    @Query("select r from Restaurant r left join fetch r.address.city " +
            "left join fetch r.address.city.state left join fetch r.kitchen where r.code = :code")
    Optional<Restaurant> findByCode(@Param("code") String uuid);

    @Query("select r from Restaurant r left join fetch r.address.city " +
            "left join fetch r.address.city.state left join fetch r.kitchen " +
            "left join fetch r.paymentMethods where r.code = :code")
    Optional<Restaurant> findRestaurantByCodeForValidation(@Param("code") String code);

    @Override
    @Query("select r from Restaurant r left join fetch r.address.city " +
            "left join fetch r.address.city.state left join fetch r.kitchen where r.id = :id")
    Optional<Restaurant> findById(@Param("id") Long id);

    @Query("select case when exists(select 1 from Restaurant r join r.responsible responsible " +
            "where r.code = :restaurantCode and responsible.id = :responsibleId) then true else false end")
    Boolean existsResponsible(@Param("restaurantCode") String restaurantCode, @Param("responsibleId") Long responsibleId);
}
