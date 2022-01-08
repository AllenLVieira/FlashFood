package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.repository.RestaurantRepository;
import br.com.allen.flashfood.domain.service.RestaurantRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                restaurantRegistration.saveRestaurant(restaurant);
                return ResponseEntity.ok(restaurant);
            }
            return ResponseEntity.notFound().build();
        } catch (EntityNotFoundedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
