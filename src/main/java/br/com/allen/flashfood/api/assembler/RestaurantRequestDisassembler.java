package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.request.RestaurantRequest;
import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantRequestDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurant toDomainObject(RestaurantRequest restaurantRequest) {
        return modelMapper.map(restaurantRequest, Restaurant.class);
    }

    public void copyToDomainObject(RestaurantRequest restaurantRequest, Restaurant restaurant) {
        // Ensure that a restaurant's cuisine can be updated.
        restaurant.setCuisine(new Cuisine());
        if (restaurant.getAddress() != null) {
            restaurant.getAddress().setCity(new City());
        }
        modelMapper.map(restaurantRequest, restaurant);
    }
}
