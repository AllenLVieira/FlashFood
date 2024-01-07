package br.com.allen.flashfood.api.model.response;

import br.com.allen.flashfood.api.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuisineResponse {
  @Schema(example = "1")
  @JsonView(RestaurantView.Summary.class)
  private Long id;

  @Schema(example = "Italian")
  @JsonView(RestaurantView.Summary.class)
  private String name;
}
