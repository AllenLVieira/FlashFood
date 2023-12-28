package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.response.DeliveryOrderResponse;
import br.com.allen.flashfood.domain.model.DeliveryOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderModelAssembler {

  private final ModelMapper modelMapper;

  public DeliveryOrderResponse toModel(DeliveryOrder order) {
    return modelMapper.map(order, DeliveryOrderResponse.class);
  }

  public List<DeliveryOrderResponse> toCollectionModel(List<DeliveryOrder> orders) {
    return orders.stream().map(this::toModel).collect(Collectors.toList());
  }
}
