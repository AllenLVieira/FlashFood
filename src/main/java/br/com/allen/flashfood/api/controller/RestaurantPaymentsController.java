package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.PaymentMethodModelAssembler;
import br.com.allen.flashfood.api.model.response.PaymentMethodResponse;
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
    value = "/restaurants/{restaurantId}/payment-methods",
    produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(
    name = "Restaurant Payments",
    description =
        "Handles the management of payment methods associated with"
            + " individual restaurants in the FlashFood application. This controller facilitates the addition,"
            + " modification, and removal of payment methods for restaurants, ensuring a versatile and efficient"
            + " payment process tailored to each dining establishment. It is integral in providing restaurant owners"
            + " the flexibility to offer various payment options to their customers.")
public class RestaurantPaymentsController {

  private final RestaurantRegistrationService restaurantRegistration;

  private final PaymentMethodModelAssembler paymentMethodModelAssembler;

  @GetMapping
  @Operation(
      description =
          "Get all the payment methos accepted in a restaurant in the Flashfood application.")
  public List<PaymentMethodResponse> getAllPaymentMethodsRestaurants(
      @PathVariable Long restaurantId) {
    Restaurant restaurant = restaurantRegistration.findRestaurantOrElseThrow(restaurantId);
    return paymentMethodModelAssembler.toCollectionModel(restaurant.getPaymentMethod());
  }

  @DeleteMapping("/{paymentMethodId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Remove a payment method in a restaurant in the Flashfood application.")
  public void removePaymentMethod(
      @PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
    restaurantRegistration.removePaymentMethod(restaurantId, paymentMethodId);
  }

  @PutMapping("/{paymentMethodId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Add new payment method in a restaurnt on the Flashfood application.")
  public void addPaymentMethod(
      @PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
    restaurantRegistration.addPaymentMethod(restaurantId, paymentMethodId);
  }
}
