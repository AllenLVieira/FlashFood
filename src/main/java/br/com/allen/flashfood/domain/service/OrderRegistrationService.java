package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.OrderNotFoundException;
import br.com.allen.flashfood.domain.model.DeliveryOrder;
import br.com.allen.flashfood.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderRegistrationService {

    private final OrderRepository orderRepository;

    public DeliveryOrder findOrderOrElseThrow(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
