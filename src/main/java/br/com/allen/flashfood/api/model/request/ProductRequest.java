package br.com.allen.flashfood.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
  @Schema(example = "Tomato soup", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  private String name;

  @Schema(example = "Accompanies starter salad.", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  private String description;

  @Schema(example = "12.5", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  @PositiveOrZero
  private BigDecimal price;

  @Schema(example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private boolean active;
}
