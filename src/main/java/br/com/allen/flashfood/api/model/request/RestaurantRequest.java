package br.com.allen.flashfood.api.model.request;

import br.com.allen.flashfood.core.validation.FreightRate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantRequest {

  @NotBlank private String name;

  @FreightRate @NotNull private BigDecimal freightRate;

  @Valid @NotNull private CuisineIDRequest cuisine;

  @Valid @NotNull private AddressRequest address;
}
