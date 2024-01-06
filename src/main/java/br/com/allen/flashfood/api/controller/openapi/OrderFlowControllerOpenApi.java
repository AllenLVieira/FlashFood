package br.com.allen.flashfood.api.controller.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
    name = "Order Flow",
    description =
        "Handles the management of the order lifecycle in the FlashFood application."
            + " This controller is responsible for updating the status of orders through different stages, such as order"
            + " acceptance, preparation, dispatch, and delivery completion. It plays a critical role in ensuring the"
            + " efficient and timely processing of food delivery orders, thereby enhancing customer experience.")
public interface OrderFlowControllerOpenApi {

  @Operation(description = "Confirm a order in the Flashfood application.")
  void confirmOrder(String orderCode);

  @Operation(description = "Update status to delivered in the Flashfood application.")
  void deliverOrder(String orderCode);

  @Operation(description = "Cancel a order in the Flashfood application.")
  void cancelOrder(String orderCode);
}
