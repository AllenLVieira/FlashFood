package br.com.allen.flashfood.domain.event;

import br.com.allen.flashfood.domain.model.DeliveryOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CancelledOrderEvent {
  private DeliveryOrder order;
}
