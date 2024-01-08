package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.controller.OrderController;
import br.com.allen.flashfood.api.controller.RestaurantController;
import br.com.allen.flashfood.api.controller.UserController;
import br.com.allen.flashfood.api.model.response.DeliveryOrderSummaryResponse;
import br.com.allen.flashfood.domain.model.DeliveryOrder;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderModelSummaryAssembler
    extends RepresentationModelAssemblerSupport<DeliveryOrder, DeliveryOrderSummaryResponse> {

  private final ModelMapper modelMapper;

  public OrderModelSummaryAssembler(ModelMapper modelMapper) {
    super(OrderController.class, DeliveryOrderSummaryResponse.class);
    this.modelMapper = modelMapper;
  }

  @Override
  public DeliveryOrderSummaryResponse toModel(DeliveryOrder order) {
    DeliveryOrderSummaryResponse response = createModelWithId(order.getId(), order);
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

    return response;
  }
  
}
