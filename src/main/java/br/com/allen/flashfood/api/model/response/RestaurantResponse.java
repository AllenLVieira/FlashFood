package br.com.allen.flashfood.api.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantResponse {
    private Long id;
    private String name;
    private BigDecimal freightRate;
    private CuisineResponse cuisine;
    private Boolean active;
    private AddressResponse address;
}
