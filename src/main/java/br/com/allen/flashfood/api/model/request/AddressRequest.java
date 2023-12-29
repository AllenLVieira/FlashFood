package br.com.allen.flashfood.api.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {

  @NotBlank private String zipCode;

  @NotBlank private String street;

  @NotBlank private String number;

  @NotBlank private String district;

  private String complement;

  @Valid @NotNull private CityIdRequest city;
}
