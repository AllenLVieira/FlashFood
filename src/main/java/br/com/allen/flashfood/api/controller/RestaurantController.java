package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.RestaurantModelAssembler;
import br.com.allen.flashfood.api.assembler.RestaurantRequestDisassembler;
import br.com.allen.flashfood.api.controller.openapi.RestaurantControllerOpenApi;
import br.com.allen.flashfood.api.model.request.RestaurantRequest;
import br.com.allen.flashfood.api.model.response.RestaurantResponse;
import br.com.allen.flashfood.api.model.view.RestaurantView;
import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.CityNotFoundException;
import br.com.allen.flashfood.domain.exception.CuisineNotFoundException;
import br.com.allen.flashfood.domain.exception.RestaurantNotFoundException;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.repository.RestaurantRepository;
import br.com.allen.flashfood.domain.service.RestaurantRegistrationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RestaurantController implements RestaurantControllerOpenApi {

  private final RestaurantRepository restaurantRepository;
  private final RestaurantRegistrationService restaurantRegistration;
  private final RestaurantModelAssembler restaurantModelAssembler;
  private final RestaurantRequestDisassembler requestDisassembler;

  /*
  Example using JsonView
  @JsonView(RestaurantView.Summary.class)
  @GetMapping(params = "projection=summary")
  public List<RestaurantResponse> getAllRestaurantsSummary() {
      return getAllRestaurants();
  }

  @JsonView(RestaurantView.OnlyName.class)
  @GetMapping(params = "projection=only-name")
  public List<RestaurantResponse> getAllRestaurantsOnlyName() {
      return getAllRestaurants();
  }*/

  /**
   * @param projection Define what fields will be returned to the response
   * @return List of all Restaurants
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public MappingJacksonValue getAllRestaurants(@RequestParam(required = false) String projection) {
    List<Restaurant> restaurants = restaurantRepository.findAll();
    List<RestaurantResponse> restaurantResponse =
        restaurantModelAssembler.toCollectionModel(restaurants);

    MappingJacksonValue restaurantWrapper = new MappingJacksonValue(restaurantResponse);
    restaurantWrapper.setSerializationView(RestaurantView.Summary.class);
    if ("only-name".equals(projection)) {
      restaurantWrapper.setSerializationView(RestaurantView.OnlyName.class);
    } else if ("full".equals(projection)) {
      restaurantWrapper.setSerializationView(null);
    }
    return restaurantWrapper;
  }

  @GetMapping(value = "/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public RestaurantResponse getRestaurantById(@PathVariable Long restaurantId) {
    Restaurant restaurant = restaurantRegistration.findRestaurantOrElseThrow(restaurantId);
    return restaurantModelAssembler.toModel(restaurant);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public RestaurantResponse addRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest) {
    try {
      Restaurant restaurant = requestDisassembler.toDomainObject(restaurantRequest);
      return restaurantModelAssembler.toModel(restaurantRegistration.saveRestaurant(restaurant));
    } catch (CuisineNotFoundException e) {
      throw new BusinessException(e.getMessage());
    }
  }

  @PutMapping(value = "/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public RestaurantResponse updateRestaurant(
      @PathVariable Long restaurantId, @RequestBody @Valid RestaurantRequest restaurantRequest) {
    try {
      Restaurant actualRestaurant = restaurantRegistration.findRestaurantOrElseThrow(restaurantId);
      requestDisassembler.copyToDomainObject(restaurantRequest, actualRestaurant);
      return restaurantModelAssembler.toModel(
          restaurantRegistration.saveRestaurant(actualRestaurant));
    } catch (CuisineNotFoundException | CityNotFoundException e) {
      throw new BusinessException(e.getMessage());
    }
  }

  @PutMapping(value = "/{restaurantId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void activateRestaurant(@PathVariable Long restaurantId) {
    restaurantRegistration.activateRestaurant(restaurantId);
  }

  @DeleteMapping("/{restaurantId}/active")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void disableRestaurant(@PathVariable Long restaurantId) {
    restaurantRegistration.disableRestaurant(restaurantId);
  }

  @PutMapping(value = "/activations", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void massActivateRestaurant(@RequestBody List<Long> restaurantIds) {
    try {
      restaurantRegistration.massActivateRestaurant(restaurantIds);
    } catch (RestaurantNotFoundException e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }

  @DeleteMapping("/activations")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void massDisableRestaurant(@RequestBody List<Long> restaurantIds) {
    try {
      restaurantRegistration.massDisableRestaurant(restaurantIds);
    } catch (RestaurantNotFoundException e) {
      throw new BusinessException(e.getMessage(), e);
    }
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
