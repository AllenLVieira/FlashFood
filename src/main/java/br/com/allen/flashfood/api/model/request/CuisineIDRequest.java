package br.com.allen.flashfood.api.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuisineIDRequest {

  @NotNull private Long id;
}
