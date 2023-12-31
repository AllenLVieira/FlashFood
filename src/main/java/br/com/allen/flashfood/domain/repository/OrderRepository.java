package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.DeliveryOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository
    extends CustomJpaRepository<DeliveryOrder, Long>, JpaSpecificationExecutor<DeliveryOrder> {

  Optional<DeliveryOrder> findByOrderCode(String orderCode);

  @Query("from DeliveryOrder d join fetch d.user join fetch d.restaurant r join fetch r.cuisine")
  List<DeliveryOrder> findAll();
}
