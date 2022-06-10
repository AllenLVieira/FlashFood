package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.OrderNotFoundException;
import br.com.allen.flashfood.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderRegistrationService {

    private final OrderRepository orderRepository;

    public Order findOrderOrElseThrow(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
