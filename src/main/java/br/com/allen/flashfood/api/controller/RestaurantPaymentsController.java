package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.PaymentMethodModelAssembler;
import br.com.allen.flashfood.api.controller.openapi.RestaurantPaymentsControllerOpenApi;
import br.com.allen.flashfood.api.model.response.PaymentMethodResponse;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.service.RestaurantRegistrationService;
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
public class RestaurantPaymentsController implements RestaurantPaymentsControllerOpenApi {

  private final RestaurantRegistrationService restaurantRegistration;
  private final PaymentMethodModelAssembler paymentMethodModelAssembler;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<PaymentMethodResponse> getAllPaymentMethodsRestaurants(
      @PathVariable Long restaurantId) {
    Restaurant restaurant = restaurantRegistration.findRestaurantOrElseThrow(restaurantId);
    return paymentMethodModelAssembler.toCollectionModel(restaurant.getPaymentMethod());
  }

  @DeleteMapping("/{paymentMethodId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removePaymentMethod(
      @PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
    restaurantRegistration.removePaymentMethod(restaurantId, paymentMethodId);
  }

  @PutMapping(value = "/{paymentMethodId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void addPaymentMethod(
      @PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
    restaurantRegistration.addPaymentMethod(restaurantId, paymentMethodId);
  }
}
