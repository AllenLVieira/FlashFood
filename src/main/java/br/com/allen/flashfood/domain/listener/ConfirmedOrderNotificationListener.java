package br.com.allen.flashfood.domain.listener;

import br.com.allen.flashfood.domain.event.ConfirmedOrderEvent;
import br.com.allen.flashfood.domain.model.DeliveryOrder;
import br.com.allen.flashfood.domain.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmedOrderNotificationListener {

  private final EmailSenderService emailService;

  @EventListener
  public void whenOrderConfirmed(ConfirmedOrderEvent event) {
    DeliveryOrder order = event.getOrder();

    var emailMessage =
        EmailSenderService.EmailMessage.builder()
            .subject(order.getRestaurant().getName() + " - Order confirmed")
            .body("orderconfirmed.html")
            .variable("order", order)
            .to(order.getUser().getEmail())
            .build();

    emailService.send(emailMessage);
  }
}
