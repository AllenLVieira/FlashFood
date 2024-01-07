package br.com.allen.flashfood.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {

  @Schema(example = "38400-000", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  private String zipCode;

  @Schema(example = "Rua Aleatoria", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  private String street;

  @Schema(example = "123", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  private String number;

  @Schema(example = "Sé", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  private String district;

  @Schema(example = "Apt. 01", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private String complement;

  @Valid @NotNull private CityIdRequest city;
}
