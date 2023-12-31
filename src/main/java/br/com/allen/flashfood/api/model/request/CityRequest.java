package br.com.allen.flashfood.api.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityRequest {
  @NotBlank private String name;

  @Valid @NotNull private StateIDRequest state;
}
