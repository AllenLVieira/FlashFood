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
    public void confirmOrder(String orderCode) {
        DeliveryOrder order = orderService.findOrderOrElseThrow(orderCode);
        order.confirm();
    }

    @Transactional
    public void deliverOrder(String orderCode) {
        DeliveryOrder order = orderService.findOrderOrElseThrow(orderCode);
        order.deliver();
    }

    @Transactional
    public void cancelOrder(String orderCode) {
        DeliveryOrder order = orderService.findOrderOrElseThrow(orderCode);
        order.cancel();
    }

}
