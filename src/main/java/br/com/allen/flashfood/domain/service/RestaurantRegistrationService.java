package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.repository.CuisineRepository;
import br.com.allen.flashfood.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantRegistrationService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CuisineRepository cuisineRepository;

    public Restaurant saveRestaurant(Restaurant restaurant) {
        Long cuisineId = restaurant.getCuisine().getId();
        Cuisine cuisine = cuisineRepository.getCuisineById(cuisineId);
        if (cuisine == null) {
            throw new EntityNotFoundedException(
                    String.format("There is no cuisine registration with code %d", cuisineId)
            );
        }
        restaurant.setCuisine(cuisine);
        return restaurantRepository.saveRestaurant(restaurant);
    }
}
