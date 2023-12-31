package br.com.allen.flashfood.api.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryOrderRequest {

  @Valid @NotNull private RestaurantIdRequest restaurant;

  @Valid @NotNull private AddressRequest address;

  @Valid @NotNull private PaymentMethodIdRequest paymentMethod;

  @Valid
  @Size(min = 1)
  @NotNull
  private List<OrderItemRequest> items;
}
