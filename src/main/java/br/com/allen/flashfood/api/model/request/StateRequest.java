package br.com.allen.flashfood.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateRequest {
  @Schema(example = "Rondônia", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  private String name;
}
