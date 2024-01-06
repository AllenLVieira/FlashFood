package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.model.request.RestaurantRequest;
import br.com.allen.flashfood.api.model.response.RestaurantResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(
    name = "Restaurant",
    description =
        "Manages all restaurant-related operations within the FlashFood"
            + " application. This controller is involved in adding new restaurants, updating existing restaurant"
            + " information, retrieving details about different restaurants, and handling restaurant deletions."
            + " It's crucial for maintaining an up-to-date and diverse listing of restaurants, enhancing"
            + " the user's dining options.")
public interface RestaurantControllerOpenApi {

  @Operation(summary = "Get all restaurants with optional projections")
  MappingJacksonValue getAllRestaurants(
      @Parameter(
              description =
                  "Projection type for restaurant data. Options: 'only-name', 'full', or none for summary view.",
              schema =
                  @Schema(
                      type = "string",
                      allowableValues = {"only-name", "full", ""}))
          String projection);

  @Operation(description = "Get a restaurant by Id in the Flashfood application.")
  RestaurantResponse getRestaurantById(@PathVariable Long restaurantId);

  @Operation(description = "Create a new restaurant in the Flashfood application.")
  RestaurantResponse addRestaurant(@Valid RestaurantRequest restaurantRequest);

  @Operation(description = "Update a restaurant in the Flashfood application.")
  RestaurantResponse updateRestaurant(
      @PathVariable Long restaurantId, @Valid RestaurantRequest restaurantRequest);

  @Operation(description = "Activate a restaurant by Id in the Flashfood application.")
  void activateRestaurant(@PathVariable Long restaurantId);

  @Operation(description = "Disable a restaurant by Id in the Flashfood application.")
  void disableRestaurant(@PathVariable Long restaurantId);

  @Operation(description = "Activate multiple restaurants by Ids in the Flashfood application.")
  void massActivateRestaurant(List<Long> restaurantIds);

  @Operation(description = "GDisable multiple restaurants by Ids in the Flashfood application.")
  void massDisableRestaurant(List<Long> restaurantIds);

  @Operation(description = "Set restaurant status to open in the Flashfood application.")
  void openRestaurant(@PathVariable Long restaurantId);

  @Operation(description = "Set restaurant status to closed in the Flashfood application.")
  void closeRestaurant(@PathVariable Long restaurantId);
}
