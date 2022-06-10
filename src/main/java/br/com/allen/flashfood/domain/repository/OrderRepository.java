package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.DeliveryOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CustomJpaRepository<DeliveryOrder, Long> {

}
