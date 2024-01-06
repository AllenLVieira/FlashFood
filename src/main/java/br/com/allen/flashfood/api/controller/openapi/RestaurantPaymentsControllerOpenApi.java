package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.model.response.PaymentMethodResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(
    name = "Restaurant Payments",
    description =
        "Handles the management of payment methods associated with"
            + " individual restaurants in the FlashFood application. This controller facilitates the addition,"
            + " modification, and removal of payment methods for restaurants, ensuring a versatile and efficient"
            + " payment process tailored to each dining establishment. It is integral in providing restaurant owners"
            + " the flexibility to offer various payment options to their customers.")
public interface RestaurantPaymentsControllerOpenApi {

  @Operation(
      description =
          "Get all the payment methos accepted in a restaurant in the Flashfood application.")
  List<PaymentMethodResponse> getAllPaymentMethodsRestaurants(Long restaurantId);

  @Operation(description = "Remove a payment method in a restaurant in the Flashfood application.")
  void removePaymentMethod(Long restaurantId, Long paymentMethodId);

  @Operation(description = "Add new payment method in a restaurnt on the Flashfood application.")
  void addPaymentMethod(Long restaurantId, Long paymentMethodId);
}
