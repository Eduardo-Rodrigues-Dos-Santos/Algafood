package org.algaworks.algafood.domain.repositories;

import org.algaworks.algafood.domain.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
