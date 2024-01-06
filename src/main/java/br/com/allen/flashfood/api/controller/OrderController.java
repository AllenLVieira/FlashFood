package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.DeliveryOrderRequestDisassembler;
import br.com.allen.flashfood.api.assembler.OrderModelAssembler;
import br.com.allen.flashfood.api.assembler.OrderModelSummaryAssembler;
import br.com.allen.flashfood.api.model.request.DeliveryOrderRequest;
import br.com.allen.flashfood.api.model.response.DeliveryOrderResponse;
import br.com.allen.flashfood.api.model.response.DeliveryOrderSummaryResponse;
import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import br.com.allen.flashfood.domain.model.DeliveryOrder;
import br.com.allen.flashfood.domain.model.User;
import br.com.allen.flashfood.domain.repository.OrderRepository;
import br.com.allen.flashfood.domain.repository.filter.DeliveryOrderFilter;
import br.com.allen.flashfood.domain.service.OrderRegistrationService;
import br.com.allen.flashfood.infrastructure.repository.spec.DeliveryOrderSpecifications;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(
    name = "Order",
    description =
        "Manages all aspects of food delivery orders in the FlashFood application. "
            + "This controller facilitates operations such as placing new orders, tracking order status, and managing"
            + " order details. It is a key component in the order processing system, ensuring a smooth user experience"
            + " from order creation to delivery.")
public class OrderController {
  private final OrderRepository orderRepository;
  private final OrderRegistrationService orderService;
  private final OrderModelSummaryAssembler orderSummaryAssembler;
  private final OrderModelAssembler orderAssembler;
  private final DeliveryOrderRequestDisassembler orderDisassembler;

  @GetMapping
  @Operation(description = "Get all the orders in the Flashfood application.")
  public MappingJacksonValue getAllOrders(@RequestParam(required = false) String fields) {
    List<DeliveryOrder> allOrders = orderRepository.findAll();
    List<DeliveryOrderSummaryResponse> deliveryOrderResponse =
        orderSummaryAssembler.toCollectionModel(allOrders);

    MappingJacksonValue orderWrapper = new MappingJacksonValue(deliveryOrderResponse);

    SimpleFilterProvider filters =
        new SimpleFilterProvider()
            .addFilter("orderFilter", SimpleBeanPropertyFilter.serializeAll());

    if (StringUtils.isNotBlank(fields)) {
      filters.addFilter(
          "orderFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
    }

    orderWrapper.setFilters(filters);
    return orderWrapper;
  }

  @GetMapping("/filters")
  @Operation(description = "Get all the orders using Filters in the Flashfood application.")
  public Page<DeliveryOrderResponse> getAllOrdersWithFilters(
      DeliveryOrderFilter filter, @PageableDefault(size = 10) Pageable pageable) {
    Page<DeliveryOrder> allOrdersPageable =
        orderRepository.findAll(DeliveryOrderSpecifications.usingFilters(filter), pageable);
    List<DeliveryOrderResponse> deliveryOrderList =
        orderAssembler.toCollectionModel(allOrdersPageable.getContent());
    return new PageImpl<>(deliveryOrderList, pageable, allOrdersPageable.getTotalElements());
  }

  @GetMapping("/{orderCode}")
  @Operation(description = "Get Order By Id Flashfood application.")
  public DeliveryOrderResponse getOrderById(@PathVariable String orderCode) {
    DeliveryOrder order = orderService.findOrderOrElseThrow(orderCode);

    return orderAssembler.toModel(order);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(description = "Create a new Order in the Flashfood application.")
  public DeliveryOrderResponse addNewDeliveryOrder(@RequestBody DeliveryOrderRequest request) {
    try {
      DeliveryOrder newOrder = orderDisassembler.toDomainObject(request);

      // TODO: Adjust when there is user authentication
      newOrder.setUser(new User());
      newOrder.getUser().setId(1L);

      newOrder = orderService.createOrder(newOrder);
      return orderAssembler.toModel(newOrder);
    } catch (EntityNotFoundedException e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }
}
