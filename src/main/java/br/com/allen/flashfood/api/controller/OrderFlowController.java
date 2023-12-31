package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.domain.service.OrderFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders/{orderCode}", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderFlowController {

  private final OrderFlowService orderFlowService;

  @PutMapping("/confirmation")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void confirmOrder(@PathVariable String orderCode) {
    orderFlowService.confirmOrder(orderCode);
  }

  @PutMapping("/delivered")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deliverOrder(@PathVariable String orderCode) {
    orderFlowService.deliverOrder(orderCode);
  }

  @PutMapping("/cancellation")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void cancelOrder(@PathVariable String orderCode) {
    orderFlowService.cancelOrder(orderCode);
  }
}
