package br.com.allen.flashfood.domain.repository;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long> {
    
}
