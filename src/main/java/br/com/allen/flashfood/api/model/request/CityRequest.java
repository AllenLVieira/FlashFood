package br.com.allen.flashfood.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityRequest {
  @Schema(example = "Porto Velho", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  private String name;

  @Valid @NotNull private StateIDRequest state;
}
