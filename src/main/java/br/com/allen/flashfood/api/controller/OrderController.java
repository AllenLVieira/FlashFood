package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.DeliveryOrderRequestDisassembler;
import br.com.allen.flashfood.api.assembler.OrderModelAssembler;
import br.com.allen.flashfood.api.assembler.OrderModelSummaryAssembler;
import br.com.allen.flashfood.api.controller.openapi.OrderControllerOpenApi;
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
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController implements OrderControllerOpenApi {
  private final OrderRepository orderRepository;
  private final OrderRegistrationService orderService;
  private final OrderModelSummaryAssembler orderSummaryAssembler;
  private final OrderModelAssembler orderAssembler;
  private final DeliveryOrderRequestDisassembler orderDisassembler;
  private final PagedResourcesAssembler<DeliveryOrder> pagedResourcesAssembler;

  /*@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
  }*/

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MappingJacksonValue> getAllOrdersWithFilters(
      DeliveryOrderFilter filter, @PageableDefault(size = 10) Pageable pageable) {

    Page<DeliveryOrder> allOrdersPageable =
        orderRepository.findAll(DeliveryOrderSpecifications.usingFilters(filter), pageable);
    PagedModel<DeliveryOrderSummaryResponse> pagedModel =
        pagedResourcesAssembler.toModel(allOrdersPageable, orderSummaryAssembler);

    MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(pagedModel);

    FilterProvider filters =
        new SimpleFilterProvider()
            .addFilter(
                "orderFilter", SimpleBeanPropertyFilter.serializeAllExcept("excludedProperty"));
    mappingJacksonValue.setFilters(filters);

    return ResponseEntity.ok(mappingJacksonValue);
  }

  @GetMapping(value = "/{orderCode}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DeliveryOrderResponse getOrderById(@PathVariable String orderCode) {
    DeliveryOrder order = orderService.findOrderOrElseThrow(orderCode);

    return orderAssembler.toModel(order);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
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
