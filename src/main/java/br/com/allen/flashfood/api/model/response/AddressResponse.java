package br.com.allen.flashfood.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {
  @Schema(example = "38400-000")
  private String zipCode;

  @Schema(example = "Rua Aleatoria")
  private String street;

  @Schema(example = "123")
  private String number;

  @Schema(example = "Sé")
  private String district;

  @Schema(example = "Apt. 01")
  private String complement;

  private CitySummaryResponse city;
}
