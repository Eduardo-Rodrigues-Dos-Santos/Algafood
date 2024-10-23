package org.algaworks.algafood.domain.repositories;

import org.algaworks.algafood.domain.models.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends CustomJpaRepository<Permission, Long> {
}
