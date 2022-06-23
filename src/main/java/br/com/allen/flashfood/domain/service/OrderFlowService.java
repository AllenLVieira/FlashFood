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

        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            throw new BusinessException(
                    String.format("Order status %d cannot be changed from %s to %s.", order.getId(),
                            order.getStatus().getDescription(), OrderStatus.CONFIRMED.getDescription()));
        }

        order.setStatus(OrderStatus.CONFIRMED);
        order.setConfirmationDate(OffsetDateTime.now());
    }
}
