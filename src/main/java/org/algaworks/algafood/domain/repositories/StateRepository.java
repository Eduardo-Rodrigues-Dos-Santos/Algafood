package org.algaworks.algafood.domain.repositories;

import org.algaworks.algafood.domain.models.State;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends CustomJpaRepository<State, Long> {
}
