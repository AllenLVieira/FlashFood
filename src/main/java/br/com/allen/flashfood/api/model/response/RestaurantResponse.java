package br.com.allen.flashfood.api.model.response;

import br.com.allen.flashfood.api.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponse {

  @Schema(example = "1")
  @JsonView({RestaurantView.Summary.class, RestaurantView.OnlyName.class})
  private Long id;

  @Schema(example = "Almanara")
  @JsonView({RestaurantView.Summary.class, RestaurantView.OnlyName.class})
  private String name;

  @Schema(example = "15.9")
  @JsonView(RestaurantView.Summary.class)
  private BigDecimal freightRate;

  @JsonView(RestaurantView.Summary.class)
  private CuisineResponse cuisine;

  private Boolean active;
  private Boolean openStatus;
  private AddressResponse address;
}
