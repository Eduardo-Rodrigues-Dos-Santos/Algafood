package org.algaworks.algafood.infrastructure.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.algaworks.algafood.domain.repositories.CustomRestaurantRepository;
import org.algaworks.algafood.domain.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantRepositoryImpl implements CustomRestaurantRepository {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Lazy
    @Autowired
    private RestaurantRepository restaurantRepository;
}















