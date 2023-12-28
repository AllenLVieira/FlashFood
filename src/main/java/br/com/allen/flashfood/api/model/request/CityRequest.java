package br.com.allen.flashfood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityRequest {
  @NotBlank private String name;

  @Valid @NotNull private StateIDRequest state;
}
