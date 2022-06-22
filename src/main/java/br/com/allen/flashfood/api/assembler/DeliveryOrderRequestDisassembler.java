package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.request.DeliveryOrderRequest;
import br.com.allen.flashfood.domain.model.DeliveryOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryOrderRequestDisassembler {

    private ModelMapper modelMapper;

    public DeliveryOrder toDomainObject(DeliveryOrderRequest request) {
        return modelMapper.map(request, DeliveryOrder.class);
    }

    public void copyToDomainObject(DeliveryOrderRequest request, DeliveryOrder deliveryOrder) {
        modelMapper.map(request, deliveryOrder);
    }
}
