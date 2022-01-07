package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.PaymentMethod;

import java.util.List;

public interface PaymentMehodRepository {
    List<PaymentMethod> getAllPaymentsMethods();

    PaymentMethod getPaymentMethodById(Long id);

    PaymentMethod savePaymentMethod(PaymentMethod paymentMethod);

    void removePaymentMethod(PaymentMethod paymentMethod);
}
