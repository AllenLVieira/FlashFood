package br.com.allen.flashfood.api.model.response;

import br.com.allen.flashfood.api.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuisineResponse {

    @JsonView(RestaurantView.Summary.class)
    private Long id;
    
    @JsonView(RestaurantView.Summary.class)
    private String name;
}
