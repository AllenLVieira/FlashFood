package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.UserModelAssembler;
import br.com.allen.flashfood.api.controller.openapi.RestaurantUserManagerControllerOpenApi;
import br.com.allen.flashfood.api.model.response.UserResponse;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.service.RestaurantRegistrationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
    value = "/restaurants/{restaurantId}/managers",
    produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RestaurantUserManagerController implements RestaurantUserManagerControllerOpenApi {

  private final RestaurantRegistrationService restaurantService;
  private final UserModelAssembler userAssembler;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<UserResponse> getAllManagers(@PathVariable Long restaurantId) {
    Restaurant restaurant = restaurantService.findRestaurantOrElseThrow(restaurantId);

    return userAssembler.toCollectionModel(restaurant.getManagers());
  }

  @DeleteMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unlinkManager(@PathVariable Long restaurantId, @PathVariable Long userId) {
    restaurantService.unlinkManager(restaurantId, userId);
  }

  @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void linkManager(@PathVariable Long restaurantId, @PathVariable Long userId) {
    restaurantService.linkManager(restaurantId, userId);
  }
}
