package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.request.PaymentMethodRequest;
import br.com.allen.flashfood.domain.model.PaymentMethod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodRequestDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public PaymentMethod toDomainObject(PaymentMethodRequest paymentMethod) {
        return modelMapper.map(paymentMethod, PaymentMethod.class);
    }

    public void copyToDomainObject(PaymentMethodRequest paymentMethodRequest, PaymentMethod paymentMethod) {
        modelMapper.map(paymentMethodRequest, paymentMethod);
    }
}
