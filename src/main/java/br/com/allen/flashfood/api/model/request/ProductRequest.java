package br.com.allen.flashfood.api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
  @NotBlank private String name;

  @NotBlank private String description;

  @NotNull @PositiveOrZero private BigDecimal price;

  @NotNull private boolean active;
}
