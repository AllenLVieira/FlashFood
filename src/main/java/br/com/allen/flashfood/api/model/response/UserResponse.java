package br.com.allen.flashfood.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
  @Schema(example = "1")
  private Long id;

  @Schema(example = "Allen Vieira")
  private String name;

  @Schema(example = "allenvieira96@gmail.com")
  private String email;
}
