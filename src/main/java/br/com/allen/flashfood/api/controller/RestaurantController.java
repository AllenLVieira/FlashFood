package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.repository.RestaurantRepository;
import br.com.allen.flashfood.domain.service.RestaurantRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantRegistrationService restaurantRegistration;

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/{restaurantId}")
    public Restaurant getRestaurantById(@PathVariable Long restaurantId) {
        return restaurantRegistration.findRestaurantOrElseThrow(restaurantId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantRegistration.saveRestaurant(restaurant);
    }

    @PutMapping("/{restaurantId}")
    public Restaurant updateRestaurant(@PathVariable Long restaurantId,
                                              @RequestBody Restaurant restaurant) {
        Restaurant actualRestaurant = restaurantRegistration.findRestaurantOrElseThrow(restaurantId);
        BeanUtils.copyProperties(restaurant, actualRestaurant, "id",
                "paymentMethod", "address", "registrationDate", "products");
        return restaurantRegistration.saveRestaurant(actualRestaurant);
    }

    @PatchMapping("/{restaurantId}")
    public Restaurant partialUpdateRestaurant(@PathVariable Long restaurantId,
                                                     @RequestBody Map<String, Object> fields) {
        Restaurant actualRestaurant = restaurantRegistration.findRestaurantOrElseThrow(restaurantId);
        mergeFields(fields, actualRestaurant);
        return updateRestaurant(restaurantId, actualRestaurant);
    }

    private void mergeFields(Map<String, Object> originFields, Restaurant target) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurant originRestaurant = objectMapper.convertValue(originFields, Restaurant.class);
        originFields.forEach((name, value) -> {
            Field field = ReflectionUtils.findField(Restaurant.class, name);
            field.setAccessible(true);
            Object newValue = ReflectionUtils.getField(field, originRestaurant);
            ReflectionUtils.setField(field, target, newValue);
        });
    }
}
