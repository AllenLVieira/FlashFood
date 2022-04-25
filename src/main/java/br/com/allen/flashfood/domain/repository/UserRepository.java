package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long> {

}
