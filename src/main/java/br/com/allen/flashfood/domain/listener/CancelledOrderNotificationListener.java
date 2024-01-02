package br.com.allen.flashfood.domain.listener;

import br.com.allen.flashfood.domain.event.CancelledOrderEvent;
import br.com.allen.flashfood.domain.model.DeliveryOrder;
import br.com.allen.flashfood.domain.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CancelledOrderNotificationListener {

  private final EmailSenderService emailService;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void whenOrderCancelled(CancelledOrderEvent event) {
    DeliveryOrder order = event.getOrder();

    var emailMessage =
        EmailSenderService.EmailMessage.builder()
            .subject(order.getRestaurant().getName() + " - Order cancelled")
            .body("ordercancelled.html")
            .variable("order", order)
            .to(order.getUser().getEmail())
            .build();

    emailService.send(emailMessage);
  }
}
