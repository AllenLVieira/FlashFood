package br.com.allen.flashfood.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

  @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private Long productId;

  @Schema(example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  @PositiveOrZero
  private Integer quantity;

  @Schema(example = "No pickles, please.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private String note;
}
