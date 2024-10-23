package org.algaworks.algafood.domain.repositories;

import org.algaworks.algafood.domain.models.City;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends CustomJpaRepository<City, Long>, JpaSpecificationExecutor<City> {

    @Query("select c from City c left join fetch c.state where c.id = :id")
    Optional<City> findById(@Param("id") Long id);
}