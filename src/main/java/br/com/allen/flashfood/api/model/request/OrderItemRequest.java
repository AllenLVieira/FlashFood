package br.com.allen.flashfood.api.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

  @NotNull private Long productId;

  @NotNull @PositiveOrZero private Integer quantity;

  private String note;
}
