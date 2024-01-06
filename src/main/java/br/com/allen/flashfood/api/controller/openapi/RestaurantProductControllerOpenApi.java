package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.model.request.ProductRequest;
import br.com.allen.flashfood.api.model.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(
    name = "Restaurant Products",
    description =
        "Manages the products or menu items for each restaurant in"
            + " the FlashFood application. This controller is responsible for adding new products to restaurant menus,"
            + " updating product information, retrieving details about specific products, and removing products as"
            + " needed. It plays a crucial role in ensuring that restaurant menus are up-to-date, diverse, and"
            + " accurately represent the culinary offerings available to customers.")
public interface RestaurantProductControllerOpenApi {

  @Operation(description = "Get all the products of a restaurant in the Flashfood application.")
  List<ProductResponse> getAllProductsByRestaurant(Long restaurantId, boolean includeInactive);

  @Operation(description = "Get specific product from restaurant in the Flashfood application.")
  ProductResponse getByRestaurantAndProduct(Long restaurantId, Long productId);

  @Operation(description = "Add new product to restaurant in the Flashfood application.")
  ProductResponse addProduct(Long restaurantId, ProductRequest productRequest);

  @Operation(description = "Update a product from restaurant id in the Flashfood application.")
  ProductResponse updateProduct(Long restaurantId, Long productId, ProductRequest productRequest);
}
