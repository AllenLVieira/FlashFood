package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.CuisineNotFoundException;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.repository.RestaurantRepository;
import br.com.allen.flashfood.domain.service.RestaurantRegistrationService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        try {
            return restaurantRegistration.saveRestaurant(restaurant);
        } catch (CuisineNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}")
    public Restaurant updateRestaurant(@PathVariable Long restaurantId,
                                       @RequestBody Restaurant restaurant) {
        Restaurant actualRestaurant = restaurantRegistration.findRestaurantOrElseThrow(restaurantId);
        BeanUtils.copyProperties(restaurant, actualRestaurant, "id",
                "paymentMethod", "address", "registrationDate", "products");
        try {
            return restaurantRegistration.saveRestaurant(actualRestaurant);
        } catch (CuisineNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PatchMapping("/{restaurantId}")
    public Restaurant partialUpdateRestaurant(@PathVariable Long restaurantId,
                                              @RequestBody Map<String, Object> fields,
                                              HttpServletRequest request) {
        Restaurant actualRestaurant = restaurantRegistration.findRestaurantOrElseThrow(restaurantId);
        mergeFields(fields, actualRestaurant, request);
        return updateRestaurant(restaurantId, actualRestaurant);
    }

    private void mergeFields(Map<String, Object> originFields, Restaurant target, HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            Restaurant originRestaurant = objectMapper.convertValue(originFields, Restaurant.class);
            originFields.forEach((name, value) -> {
                Field field = ReflectionUtils.findField(Restaurant.class, name);
                field.setAccessible(true);
                Object newValue = ReflectionUtils.getField(field, originRestaurant);
                ReflectionUtils.setField(field, target, newValue);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }
}
