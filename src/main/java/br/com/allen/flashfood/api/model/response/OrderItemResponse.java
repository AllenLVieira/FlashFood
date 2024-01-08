package br.com.allen.flashfood.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class OrderItemResponse extends RepresentationModel<OrderItemResponse> {
  @Schema(example = "1")
  private Long productId;

  @Schema(example = "Tomato Soup")
  private String productName;

  @Schema(example = "2")
  private Integer quantity;

  @Schema(example = "20.0")
  private BigDecimal unitPrice;

  @Schema(example = "40.0")
  private BigDecimal totalPrice;

  @Schema(example = "No pickles, please.")
  private String note;
}
