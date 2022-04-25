package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.RestaurantNotFoundException;
import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.model.PaymentMethod;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantRegistrationService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CuisineRegistrationService cuisineRegistration;

    @Autowired
    private CityRegistrationService cityRegistration;

    @Autowired
    private PaymentMethodRegistrationService paymentMethodRegistrationService;

    @Transactional
    public Restaurant saveRestaurant(Restaurant restaurant) {
        Long cuisineId = restaurant.getCuisine().getId();
        Long cityId = restaurant.getAddress().getCity().getId();
        Cuisine cuisine = cuisineRegistration.findCuisineOrElseThrow(cuisineId);
        City city = cityRegistration.findCityOrElseThrow(cityId);
        restaurant.setCuisine(cuisine);
        restaurant.getAddress().setCity(city);
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void activateRestaurant(Long restaurantId) {
        Restaurant actualRestaurant = findRestaurantOrElseThrow(restaurantId);
        actualRestaurant.activate();
    }

    @Transactional
    public void disableRestaurant(Long restaurantId) {
        Restaurant actualRestaurant = findRestaurantOrElseThrow(restaurantId);
        actualRestaurant.disable();
    }

    @Transactional
    public void removePaymentMethod(Long restaurantId, Long paymentMethodId) {
        Restaurant restaurant = findRestaurantOrElseThrow(restaurantId);
        PaymentMethod paymentMethod = paymentMethodRegistrationService.findPaymentMethodOrElseThrow(paymentMethodId);
        restaurant.removePaymentMethod(paymentMethod);
    }

    @Transactional
    public void addPaymentMethod(Long restaurantId, Long paymentMethodId) {
        Restaurant restaurant = findRestaurantOrElseThrow(restaurantId);
        PaymentMethod paymentMethod = paymentMethodRegistrationService.findPaymentMethodOrElseThrow(paymentMethodId);
        restaurant.addPaymentMethod(paymentMethod);
    }

    public Restaurant findRestaurantOrElseThrow(Long cuisineId) {
        return restaurantRepository.findById(cuisineId)
                .orElseThrow(() -> new RestaurantNotFoundException(cuisineId));
    }
}
