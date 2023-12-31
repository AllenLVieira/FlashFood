package br.com.allen.flashfood.api.model.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponse {

  private Long productId;
  private String productName;
  private Integer quantity;
  private BigDecimal unitPrice;
  private BigDecimal totalPrice;
  private String note;
}
