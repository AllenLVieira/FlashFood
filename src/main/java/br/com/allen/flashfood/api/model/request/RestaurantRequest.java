package br.com.allen.flashfood.api.model.request;

import br.com.allen.flashfood.core.validation.FreightRate;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantRequest {

  @NotBlank private String name;

  @FreightRate @NotNull private BigDecimal freightRate;

  @Valid @NotNull private CuisineIDRequest cuisine;

  @Valid @NotNull private AddressRequest address;
}
