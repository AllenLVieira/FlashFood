package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.model.request.ProductPhotoRequest;
import br.com.allen.flashfood.api.model.response.PhotoProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

@Tag(
    name = "Restaurant Product Photo",
    description =
        "Manages the photos of products for restaurants in the"
            + " FlashFood application. This controller allows for the uploading, updating, retrieving, and deletion"
            + " of product photos, enhancing the visual presentation of menu items. It plays a vital role in providing"
            + " a visually appealing and accurate depiction of the culinary offerings available at different restaurants.")
public interface RestaurantProductPhotoControllerOpenApi {

  @Operation(description = "Upload product photo in the Flashfood application.")
  PhotoProductResponse updatePhoto(
      Long restaurantId, Long productId, @Valid ProductPhotoRequest productPhotoRequest)
      throws IOException;

  @Operation(description = "Get photo details from product in the Flashfood application.")
  PhotoProductResponse find(Long restaurantId, Long productId);

  @Operation(description = "Get product photo from restaurant in the Flashfood application.")
  ResponseEntity<?> getPhoto(Long restaurantId, Long productId, String acceptHeader)
      throws HttpMediaTypeNotAcceptableException;

  @Operation(description = "Remove product photo in the Flashfood application.")
  void remove(Long restaurantId, Long productId);
}
