package br.com.allen.flashfood.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
  @Schema(example = "1")
  private Long id;

  @Schema(example = "Tomato Soup")
  private String name;

  @Schema(example = "Accompanies starter salad.")
  private String description;

  @Schema(example = "20.0")
  private BigDecimal price;

  @Schema(example = "true")
  private Boolean active;
}
