package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.model.request.CuisineRequest;
import br.com.allen.flashfood.api.model.response.CuisineResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Tag(
    name = "Cuisine",
    description =
        "Handles all operations related to cuisines in the FlashFood application. This controller facilitates the"
            + " creation, retrieval, updating, and deletion of cuisine information. It plays a key role in"
            + " managing the diverse culinary categories and preferences associated with the food items offered"
            + " by the application.")
public interface CuisineControllerOpenApi {

  @Operation(description = "Get all the cuisines in the Flashfood application.")
  PagedModel<CuisineResponse> getAllCuisine(Pageable pageable);

  @Operation(description = "Get a cuisine by an Id in the Flashfood application.")
  CuisineResponse getCuisineById(Long cuisineId);
  
  @Operation(description = "Add a new cuisine in the Flashfood application.")
  CuisineResponse addCuisine(CuisineRequest cuisineRequest);
  
  @Operation(description = "Update a cuisine based in specific Id in the Flashfood application.")
  CuisineResponse updateCuisine(
      Long cuisineId, CuisineRequest cuisineRequest);
  
  @Operation(description = "Delete a cuisine by an Id in the Flashfood application.")
  void deleteCuisineById(Long cuisineId);
}
