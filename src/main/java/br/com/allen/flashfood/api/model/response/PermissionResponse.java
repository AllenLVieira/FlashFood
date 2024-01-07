package br.com.allen.flashfood.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionResponse {
  @Schema(example = "1")
  private Long id;

  @Schema(example = "EDIT_PRODUCTS")
  private String name;

  @Schema(example = "Can edit products")
  private String description;
}
