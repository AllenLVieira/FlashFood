package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.domain.service.OrderFlowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders/{orderCode}", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(
    name = "Order Flow",
    description =
        "Handles the management of the order lifecycle in the FlashFood application."
            + " This controller is responsible for updating the status of orders through different stages, such as order"
            + " acceptance, preparation, dispatch, and delivery completion. It plays a critical role in ensuring the"
            + " efficient and timely processing of food delivery orders, thereby enhancing customer experience.")
public class OrderFlowController {

  private final OrderFlowService orderFlowService;

  @PutMapping("/confirmation")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Confirm a order in the Flashfood application.")
  public void confirmOrder(@PathVariable String orderCode) {
    orderFlowService.confirmOrder(orderCode);
  }

  @PutMapping("/delivered")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Update status to delivered in the Flashfood application.")
  public void deliverOrder(@PathVariable String orderCode) {
    orderFlowService.deliverOrder(orderCode);
  }

  @PutMapping("/cancellation")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Cancel a order in the Flashfood application.")
  public void cancelOrder(@PathVariable String orderCode) {
    orderFlowService.cancelOrder(orderCode);
  }
}
