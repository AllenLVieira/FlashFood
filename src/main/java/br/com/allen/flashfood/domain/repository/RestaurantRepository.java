package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> getAllRestaurants();

    Restaurant getRestaurantById(Long id);

    Restaurant saveRestaurant(Restaurant restaurant);

    void deleteRestaurant(Restaurant restaurant);
}
