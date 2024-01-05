package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.PaymentMethod;
import java.time.OffsetDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMehodRepository extends JpaRepository<PaymentMethod, Long> {

  @Query("SELECT MAX(updateDate) FROM PaymentMethod")
  OffsetDateTime getLastUpdatedOffsetDate();

  @Query("SELECT MAX(updateDate) FROM PaymentMethod WHERE id =:id")
  OffsetDateTime getLastUpdatedOffsetDateById(Long id);
}
