package br.com.allen.flashfood.api.model.response;

import br.com.allen.flashfood.api.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantResponse {

    @JsonView({RestaurantView.Summary.class, RestaurantView.OnlyName.class})
    private Long id;

    @JsonView({RestaurantView.Summary.class, RestaurantView.OnlyName.class})
    private String name;

    @JsonView(RestaurantView.Summary.class)
    private BigDecimal freightRate;

    @JsonView(RestaurantView.Summary.class)
    private CuisineResponse cuisine;

    private Boolean active;
    private Boolean openStatus;
    private AddressResponse address;
}
