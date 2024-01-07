package br.com.allen.flashfood.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
  @Schema(example = "Allen Vieira", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  private String name;

  @Schema(example = "allenvieira96@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  @Email
  private String email;
}
