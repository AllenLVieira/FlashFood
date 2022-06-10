package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.response.DeliveryOrderResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderModelAssembler {

    private final ModelMapper modelMapper;

    public DeliveryOrderResponse toModel(Order order) {
        return modelMapper.map(order, DeliveryOrderResponse.class);
    }

    public List<DeliveryOrderResponse> toCollectionModel(List<Order> orders) {
        return orders.stream()
                .map(order -> toModel(order))
                .collect(Collectors.toList());
    }
}
