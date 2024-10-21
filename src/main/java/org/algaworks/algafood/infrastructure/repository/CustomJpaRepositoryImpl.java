package org.algaworks.algafood.infrastructure.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.algaworks.algafood.domain.repositories.CustomJpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

    private final EntityManager entityManager;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public T findFist(String name) {
        TypedQuery<T> query = entityManager.createQuery("select e from " + getDomainClass()
                + "where e.name like :name limit 1", getDomainClass());
        query.setParameter("name", "%" + name + "%");
        return query.getSingleResult();
    }

    @Override
    public void detach(T entity) {
        entityManager.detach(entity);
    }
}

