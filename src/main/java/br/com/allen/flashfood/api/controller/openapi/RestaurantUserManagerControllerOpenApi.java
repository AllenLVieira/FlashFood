package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.model.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(
    name = "Restaurant User Management",
    description =
        "Handles the management of users associated with"
            + " restaurants in the FlashFood application. This controller is responsible for associating users with"
            + " specific restaurants, updating user roles or permissions related to restaurant management, and"
            + " maintaining user information pertinent to restaurant operations. It is essential for ensuring proper"
            + " management and access control in the restaurant's user base.")
public interface RestaurantUserManagerControllerOpenApi {

  @Operation(description = "Get all the managers in the Flashfood application.")
  CollectionModel<UserResponse> getAllManagers(Long restaurantId);

  @Operation(description = "Remove the manager role from user in the Flashfood application.")
  void unlinkManager(Long restaurantId, Long userId);

  @Operation(description = "Add manager role to userId in the Flashfood application.")
  void linkManager(Long restaurantId, Long userId);
}
