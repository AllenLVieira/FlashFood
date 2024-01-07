package br.com.allen.flashfood.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordRequest extends UserRequest {
  @Schema(example = "123", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  private String password;
}
