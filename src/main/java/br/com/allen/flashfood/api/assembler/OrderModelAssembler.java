package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.controller.*;
import br.com.allen.flashfood.api.model.response.DeliveryOrderResponse;
import br.com.allen.flashfood.domain.model.DeliveryOrder;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderModelAssembler
    extends RepresentationModelAssemblerSupport<DeliveryOrder, DeliveryOrderResponse> {

  private final ModelMapper modelMapper;

  public OrderModelAssembler(ModelMapper modelMapper) {
    super(OrderController.class, DeliveryOrderResponse.class);
    this.modelMapper = modelMapper;
  }

  @Override
  public DeliveryOrderResponse toModel(DeliveryOrder order) {
    DeliveryOrderResponse response = createModelWithId(order.getOrderCode(), order);
    modelMapper.map(order, response);

    response.add(WebMvcLinkBuilder.linkTo(OrderController.class).withRel("orders"));
    response
        .getRestaurant()
        .add(
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(RestaurantController.class)
                        .getRestaurantById(order.getRestaurant().getId()))
                .withSelfRel());
    response
        .getUser()
        .add(
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(UserController.class)
                        .getUserById(order.getUser().getId()))
                .withSelfRel());
    response
        .getPaymentMethod()
        .add(
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PaymentMethodController.class)
                        .getPaymentMethodById(order.getPaymentMethod().getId(), null))
                .withSelfRel());

    response
        .getDeliveryAddress()
        .getCity()
        .add(
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(CityController.class)
                        .getCityById(order.getDeliveryAddress().getCity().getId()))
                .withSelfRel());

    response
        .getItems()
        .forEach(
            item ->
                item.add(
                    WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(RestaurantProductController.class)
                                .getByRestaurantAndProduct(
                                    response.getRestaurant().getId(), item.getProductId()))
                        .withRel("produto")));

    return response;
  }
  
}
