package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.response.PaymentMethodResponse;
import br.com.allen.flashfood.domain.model.PaymentMethod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMethodModelAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public PaymentMethodResponse toModel(PaymentMethod paymentMethod) {
        return modelMapper.map(paymentMethod, PaymentMethodResponse.class);
    }

    public List<PaymentMethodResponse> toCollectionModel(Collection<PaymentMethod> paymentMethodList) {
        return paymentMethodList.stream()
                .map(paymentMethod -> toModel(paymentMethod))
                .collect(Collectors.toList());
    }
}
