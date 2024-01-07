package br.com.allen.flashfood.api.model.request;

import br.com.allen.flashfood.core.validation.FreightRate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantRequest {

  @Schema(example = "Almanara", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  private String name;

  @Schema(example = "10.9", requiredMode = Schema.RequiredMode.REQUIRED)
  @FreightRate
  @NotNull
  private BigDecimal freightRate;

  @Valid @NotNull private CuisineIDRequest cuisine;

  @Valid @NotNull private AddressRequest address;
}
