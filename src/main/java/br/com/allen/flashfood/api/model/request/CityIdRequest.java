package br.com.allen.flashfood.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityIdRequest {

  @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private Long id;
}
