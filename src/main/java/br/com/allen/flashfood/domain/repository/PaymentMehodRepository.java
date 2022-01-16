package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMehodRepository extends JpaRepository<PaymentMethod, Long> {
}
