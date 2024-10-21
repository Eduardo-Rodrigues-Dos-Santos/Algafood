package org.algaworks.algafood.domain.repositories;

import org.algaworks.algafood.domain.models.Group;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CustomJpaRepository<Group, Long> {
}
