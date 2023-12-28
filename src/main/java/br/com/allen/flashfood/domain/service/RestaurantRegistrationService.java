package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.RestaurantNotFoundException;
import br.com.allen.flashfood.domain.model.*;
import br.com.allen.flashfood.domain.repository.RestaurantRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantRegistrationService {

    private final RestaurantRepository restaurantRepository;
    private final CuisineRegistrationService cuisineRegistration;
    private final CityRegistrationService cityRegistration;
    private final PaymentMethodRegistrationService paymentMethodRegistrationService;
    private final UserRegistrationsService userService;

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
    public void massActivateRestaurant(List<Long> restaurantIds) {
        restaurantIds.forEach(this::activateRestaurant);
    }

    @Transactional
    public void massDisableRestaurant(List<Long> restaurantIds) {
        restaurantIds.forEach(this::disableRestaurant);
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

    @Transactional
    public void openRestaurant(Long restaurantId) {
        Restaurant actualRestaurant = findRestaurantOrElseThrow(restaurantId);
        actualRestaurant.openRestaurant();
    }

    @Transactional
    public void closeRestaurant(Long restaurantId) {
        Restaurant actualRestaurant = findRestaurantOrElseThrow(restaurantId);
        actualRestaurant.closeRestaurant();
    }

    @Transactional
    public void unlinkManager(Long restaurantId, Long userId) {
        Restaurant restaurant = findRestaurantOrElseThrow(restaurantId);
        User user = userService.findUserOrElseThrow(userId);

        restaurant.removeManager(user);
    }

    @Transactional
    public void linkManager(Long restaurantId, Long userId) {
        Restaurant restaurant = findRestaurantOrElseThrow(restaurantId);
        User user = userService.findUserOrElseThrow(userId);

        restaurant.addNewManager(user);
    }

    public Restaurant findRestaurantOrElseThrow(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }
}
