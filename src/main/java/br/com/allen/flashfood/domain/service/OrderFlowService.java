package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.model.DeliveryOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class OrderFlowService {

    private final OrderRegistrationService orderService;

    @Transactional
    public void confirmOrder(Long orderId) {
        DeliveryOrder order = orderService.findOrderOrElseThrow(orderId);
        order.confirm();
    }

    @Transactional
    public void deliverOrder(Long orderId) {
        DeliveryOrder order = orderService.findOrderOrElseThrow(orderId);
        order.deliver();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        DeliveryOrder order = orderService.findOrderOrElseThrow(orderId);
        order.cancel();
    }

}
