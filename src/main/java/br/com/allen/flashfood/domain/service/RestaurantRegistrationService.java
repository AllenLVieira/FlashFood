package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantRegistrationService {
    private static final String RESTAURANT_NOT_FOUND = "There is no restaurant register with code %d";
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CuisineRegistrationService cuisineRegistration;

    public Restaurant saveRestaurant(Restaurant restaurant) {
        Long cuisineId = restaurant.getCuisine().getId();
        Cuisine cuisine = cuisineRegistration.findCuisineOrElseThrow(cuisineId);
        restaurant.setCuisine(cuisine);
        return restaurantRepository.save(restaurant);
    }

    public Restaurant findRestaurantOrElseThrow(Long cuisineId) {
        return restaurantRepository.findById(cuisineId)
                .orElseThrow(() -> new EntityNotFoundedException(
                        String.format(RESTAURANT_NOT_FOUND, cuisineId)
                ));
    }
}
