package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.model.request.DeliveryOrderRequest;
import br.com.allen.flashfood.api.model.response.DeliveryOrderResponse;
import br.com.allen.flashfood.domain.repository.filter.DeliveryOrderFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

@Tag(
    name = "Order",
    description =
        "Manages all aspects of food delivery orders in the FlashFood application. "
            + "This controller facilitates operations such as placing new orders, tracking order status, and managing"
            + " order details. It is a key component in the order processing system, ensuring a smooth user experience"
            + " from order creation to delivery.")
public interface OrderControllerOpenApi {

  /*@Operation(description = "Get all the orders in the Flashfood application.")
  @ApiResponse(
      responseCode = "200",
      description = "OK",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = DeliveryOrderResponse.class)))
  MappingJacksonValue getAllOrders(String fields);*/

  @Operation(description = "Get all the orders using Filters in the Flashfood application.")
  ResponseEntity<MappingJacksonValue> getAllOrdersWithFilters(
      DeliveryOrderFilter filter, Pageable pageable);

  @Operation(description = "Get Order By Id Flashfood application.")
  DeliveryOrderResponse getOrderById(String orderCode);

  @Operation(description = "Create a new Order in the Flashfood application.")
  DeliveryOrderResponse addNewDeliveryOrder(DeliveryOrderRequest request);
}
