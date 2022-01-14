package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.repository.RestaurantRepository;
import br.com.allen.flashfood.domain.service.RestaurantRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return restaurantRepository.getAllRestaurants();
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantRepository.getRestaurantById(restaurantId);
        if (restaurant != null) {
            return ResponseEntity.ok(restaurant);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> addRestaurant(@RequestBody Restaurant restaurant) {
        try {
            restaurant = restaurantRegistration.saveRestaurant(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(restaurant);
        } catch (EntityNotFoundedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<?> updateRestaurant(@PathVariable Long restaurantId,
                                              @RequestBody Restaurant restaurant) {
        try {
            Restaurant actualRestaurant = restaurantRepository.getRestaurantById(restaurantId);
            if (actualRestaurant != null) {
                BeanUtils.copyProperties(restaurant, actualRestaurant, "id");
                actualRestaurant = restaurantRegistration.saveRestaurant(actualRestaurant);
                return ResponseEntity.ok(actualRestaurant);
            }
            return ResponseEntity.notFound().build();
        } catch (EntityNotFoundedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{restaurantId}")
    public ResponseEntity<?> partialUpdateRestaurant(@PathVariable Long restaurantId,
                                                     @RequestBody Map<String, Object> fields) {
        Restaurant actualRestaurant = restaurantRepository.getRestaurantById(restaurantId);
        if (actualRestaurant == null) {
            return ResponseEntity.notFound().build();
        }
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
