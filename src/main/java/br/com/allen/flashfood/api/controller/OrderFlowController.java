package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.controller.openapi.OrderFlowControllerOpenApi;
import br.com.allen.flashfood.domain.service.OrderFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders/{orderCode}", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderFlowController implements OrderFlowControllerOpenApi {

  private final OrderFlowService orderFlowService;

  @PutMapping(value = "/confirmation", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void confirmOrder(@PathVariable String orderCode) {
    orderFlowService.confirmOrder(orderCode);
  }

  @PutMapping(value = "/delivered", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deliverOrder(@PathVariable String orderCode) {
    orderFlowService.deliverOrder(orderCode);
  }

  @PutMapping(value = "/cancellation", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void cancelOrder(@PathVariable String orderCode) {
    orderFlowService.cancelOrder(orderCode);
  }
}
