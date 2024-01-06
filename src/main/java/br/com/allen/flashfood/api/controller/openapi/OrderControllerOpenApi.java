package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.model.request.DeliveryOrderRequest;
import br.com.allen.flashfood.api.model.response.DeliveryOrderResponse;
import br.com.allen.flashfood.domain.repository.filter.DeliveryOrderFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.converter.json.MappingJacksonValue;

@Tag(
    name = "Order",
    description =
        "Manages all aspects of food delivery orders in the FlashFood application. "
            + "This controller facilitates operations such as placing new orders, tracking order status, and managing"
            + " order details. It is a key component in the order processing system, ensuring a smooth user experience"
            + " from order creation to delivery.")
public interface OrderControllerOpenApi {

  @Operation(description = "Get all the orders in the Flashfood application.")
  MappingJacksonValue getAllOrders(String fields);

  @Operation(description = "Get all the orders using Filters in the Flashfood application.")
  Page<DeliveryOrderResponse> getAllOrdersWithFilters(
      DeliveryOrderFilter filter, @PageableDefault(size = 10) Pageable pageable);

  @Operation(description = "Get Order By Id Flashfood application.")
  DeliveryOrderResponse getOrderById(String orderCode);

  @Operation(description = "Create a new Order in the Flashfood application.")
  DeliveryOrderResponse addNewDeliveryOrder(DeliveryOrderRequest request);
}
