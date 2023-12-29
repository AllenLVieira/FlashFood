package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.response.DeliveryOrderSummaryResponse;
import br.com.allen.flashfood.domain.model.DeliveryOrder;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderModelSummaryAssembler {

  private final ModelMapper modelMapper;

  public DeliveryOrderSummaryResponse toModel(DeliveryOrder order) {
    return modelMapper.map(order, DeliveryOrderSummaryResponse.class);
  }

  public List<DeliveryOrderSummaryResponse> toCollectionModel(List<DeliveryOrder> orders) {
    return orders.stream().map(order -> toModel(order)).collect(Collectors.toList());
  }
}
