package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.OrderModelAssembler;
import br.com.allen.flashfood.api.assembler.OrderModelSummaryAssembler;
import br.com.allen.flashfood.api.model.response.DeliveryOrderResponse;
import br.com.allen.flashfood.api.model.response.DeliveryOrderSummaryResponse;
import br.com.allen.flashfood.domain.model.DeliveryOrder;
import br.com.allen.flashfood.domain.repository.OrderRepository;
import br.com.allen.flashfood.domain.service.OrderRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderRegistrationService orderService;
    private final OrderModelSummaryAssembler orderSummaryAssembler;
    private final OrderModelAssembler orderAssembler;

    @GetMapping
    public List<DeliveryOrderSummaryResponse> getAllOrders() {
        List<DeliveryOrder> allOrders = orderRepository.findAll();

        return orderSummaryAssembler.toCollectionModel(allOrders);
    }

    @GetMapping("/{orderId}")
    public DeliveryOrderResponse getOrderById(@PathVariable Long orderId) {
        DeliveryOrder order = orderService.findOrderOrElseThrow(orderId);

        return orderAssembler.toModel(order);
    }
}
