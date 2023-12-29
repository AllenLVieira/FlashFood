package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
