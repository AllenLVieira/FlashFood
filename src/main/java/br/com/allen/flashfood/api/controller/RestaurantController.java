package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.RestaurantModelAssembler;
import br.com.allen.flashfood.api.assembler.RestaurantRequestDisassembler;
import br.com.allen.flashfood.api.model.request.RestaurantRequest;
import br.com.allen.flashfood.api.model.response.RestaurantResponse;
import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.CityNotFoundException;
import br.com.allen.flashfood.domain.exception.CuisineNotFoundException;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.repository.RestaurantRepository;
import br.com.allen.flashfood.domain.service.RestaurantRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantRegistrationService restaurantRegistration;

    @Autowired
    private RestaurantModelAssembler restaurantModelAssembler;

    @Autowired
    private RestaurantRequestDisassembler requestDisassembler;

    @GetMapping
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantModelAssembler.toCollectionModel(restaurantRepository.findAll());
    }

    @GetMapping("/{restaurantId}")
    public RestaurantResponse getRestaurantById(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantRegistration.findRestaurantOrElseThrow(restaurantId);
        return restaurantModelAssembler.toModel(restaurant);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantResponse addRestaurant(
            @RequestBody @Valid RestaurantRequest restaurantRequest) {
        try {
            Restaurant restaurant = requestDisassembler.toDomainObject(restaurantRequest);
            return restaurantModelAssembler.toModel(restaurantRegistration.saveRestaurant(restaurant));
        } catch (CuisineNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}")
    public RestaurantResponse updateRestaurant(@PathVariable Long restaurantId,
                                               @RequestBody @Valid RestaurantRequest restaurantRequest) {
        try {
            Restaurant actualRestaurant = restaurantRegistration.findRestaurantOrElseThrow(restaurantId);
            requestDisassembler.copyToDomainObject(restaurantRequest, actualRestaurant);
            return restaurantModelAssembler.toModel(restaurantRegistration.saveRestaurant(actualRestaurant));
        } catch (CuisineNotFoundException | CityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateRestaurant(@PathVariable Long restaurantId) {
        restaurantRegistration.activateRestaurant(restaurantId);
    }

    @DeleteMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableRestaurant(@PathVariable Long restaurantId) {
        restaurantRegistration.disableRestaurant(restaurantId);
    }

    @PutMapping("/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void massActivateRestaurant(@RequestBody List<Long> restaurantIds) {
        restaurantRegistration.massActivateRestaurant(restaurantIds);
    }

    @DeleteMapping("/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void massDisableRestaurant(@RequestBody List<Long> restaurantIds) {
        restaurantRegistration.massDisableRestaurant(restaurantIds);
    }


    @PutMapping("/{restaurantId}/open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void openRestaurant(@PathVariable Long restaurantId) {
        restaurantRegistration.openRestaurant(restaurantId);
    }

    @PutMapping("/{restaurantId}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeRestaurant(@PathVariable Long restaurantId) {
        restaurantRegistration.closeRestaurant(restaurantId);
    }
}
