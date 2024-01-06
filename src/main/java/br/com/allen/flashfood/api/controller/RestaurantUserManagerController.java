package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.UserModelAssembler;
import br.com.allen.flashfood.api.model.response.UserResponse;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.service.RestaurantRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
    name = "Restaurant User Management",
    description =
        "Handles the management of users associated with"
            + " restaurants in the FlashFood application. This controller is responsible for associating users with"
            + " specific restaurants, updating user roles or permissions related to restaurant management, and"
            + " maintaining user information pertinent to restaurant operations. It is essential for ensuring proper"
            + " management and access control in the restaurant's user base.")
public class RestaurantUserManagerController {

  private final RestaurantRegistrationService restaurantService;
  private final UserModelAssembler userAssembler;

  @GetMapping
  @Operation(description = "Get all the managers in the Flashfood application.")
  public List<UserResponse> getAllManagers(@PathVariable Long restaurantId) {
    Restaurant restaurant = restaurantService.findRestaurantOrElseThrow(restaurantId);

    return userAssembler.toCollectionModel(restaurant.getManagers());
  }

  @DeleteMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Remove the manager role from user in the Flashfood application.")
  public void unlinkManager(@PathVariable Long restaurantId, @PathVariable Long userId) {
    restaurantService.unlinkManager(restaurantId, userId);
  }

  @PutMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Add manager role to userId in the Flashfood application.")
  public void linkManager(@PathVariable Long restaurantId, @PathVariable Long userId) {
    restaurantService.linkManager(restaurantId, userId);
  }
}
