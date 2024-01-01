package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.model.DeliveryOrder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFlowService {

  private final OrderRegistrationService orderService;
  private final EmailSenderService emailService;

  @Transactional
  public void confirmOrder(String orderCode) {
    DeliveryOrder order = orderService.findOrderOrElseThrow(orderCode);
    order.confirm();

    var emailMessage =
        EmailSenderService.EmailMessage.builder()
            .subject(order.getRestaurant().getName() + " - Order confirmed")
            .body("orderconfirmed.html")
            .variable("order", order)
            .to(order.getUser().getEmail())
            .build();

    emailService.send(emailMessage);
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
