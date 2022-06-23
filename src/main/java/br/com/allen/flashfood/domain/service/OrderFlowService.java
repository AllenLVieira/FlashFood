package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.model.DeliveryOrder;
import br.com.allen.flashfood.domain.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class OrderFlowService {

    private final OrderRegistrationService orderService;

    @Transactional
    public void confirmOrder(Long orderId) {
        DeliveryOrder order = orderService.findOrderOrElseThrow(orderId);

        validateOrderStatus(order, OrderStatus.CREATED, OrderStatus.CONFIRMED);

        order.setStatus(OrderStatus.CONFIRMED);
        order.setConfirmationDate(OffsetDateTime.now());
    }

    @Transactional
    public void deliverOrder(Long orderId) {
        DeliveryOrder order = orderService.findOrderOrElseThrow(orderId);

        validateOrderStatus(order, OrderStatus.CONFIRMED, OrderStatus.DELIVERED);

        order.setStatus(OrderStatus.DELIVERED);
        order.setDeliveryDate(OffsetDateTime.now());
    }

    /**
     * @param order        Order to be validate
     * @param sourceStatus Initial status required for validation
     * @param targetStatus Desired status on conversion
     */
    private void validateOrderStatus(DeliveryOrder order, OrderStatus sourceStatus, OrderStatus targetStatus) {
        if (!order.getStatus().equals(sourceStatus)) {
            throw new BusinessException(
                    String.format("Order status %d cannot be changed from %s to %s.", order.getId(),
                            order.getStatus().getDescription(), targetStatus.getDescription()));
        }
    }
}
