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
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderRegistrationService orderService;
    private final OrderModelSummaryAssembler orderSummaryAssembler;
    private final OrderModelAssembler orderAssembler;
    private final DeliveryOrderRequestDisassembler orderDisassembler;

    @GetMapping
    public MappingJacksonValue getAllOrders(@RequestParam(required = false) String fields) {
        List<DeliveryOrder> allOrders = orderRepository.findAll();
        List<DeliveryOrderSummaryResponse> deliveryOrderResponse = orderSummaryAssembler.toCollectionModel(allOrders);

        MappingJacksonValue orderWrapper = new MappingJacksonValue(deliveryOrderResponse);

        SimpleFilterProvider filters = new SimpleFilterProvider()
                .addFilter("orderFilter",
                        SimpleBeanPropertyFilter.serializeAll());

        if (StringUtils.isNotBlank(fields)) {
            filters.addFilter("orderFilter",
                    SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
        }

        orderWrapper.setFilters(filters);
        return orderWrapper;
    }

    @GetMapping("/filters")
    public List<DeliveryOrderResponse> getAllOrdersWithFilters(DeliveryOrderFilter filter) {
        List<DeliveryOrder> allOrders = orderRepository.findAll(DeliveryOrderSpecifications.usingFilters(filter));
        return orderAssembler.toCollectionModel(allOrders);
    }

    @GetMapping("/{orderCode}")
    public DeliveryOrderResponse getOrderById(@PathVariable String orderCode) {
        DeliveryOrder order = orderService.findOrderOrElseThrow(orderCode);

        return orderAssembler.toModel(order);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeliveryOrderResponse addNewDeliveryOrder(@RequestBody DeliveryOrderRequest request) {
        try {
            DeliveryOrder newOrder = orderDisassembler.toDomainObject(request);

            //TODO: Adjust when there is user authentication
            newOrder.setUser(new User());
            newOrder.getUser().setId(1L);

            newOrder = orderService.createOrder(newOrder);
            return orderAssembler.toModel(newOrder);
        } catch (EntityNotFoundedException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
}
