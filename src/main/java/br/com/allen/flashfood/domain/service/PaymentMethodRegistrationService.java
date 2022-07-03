package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.PaymentMethodNotFoundException;
import br.com.allen.flashfood.domain.model.PaymentMethod;
import br.com.allen.flashfood.domain.repository.PaymentMehodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentMethodRegistrationService {

    public static final String PAYMENT_METHOD_IN_USE = "Payment Method with %d code cannot be removed because it is in use.";

    private final PaymentMehodRepository paymentMehodRepository;

    @Transactional
    public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod) {
        return paymentMehodRepository.save(paymentMethod);
    }

    @Transactional
    public void deletePaymentMethod(Long paymentMethodId) {
        try {
            paymentMehodRepository.deleteById(paymentMethodId);
            paymentMehodRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PaymentMethodNotFoundException(paymentMethodId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(PAYMENT_METHOD_IN_USE, paymentMethodId)
            );
        }
    }

    public PaymentMethod findPaymentMethodOrElseThrow(Long paymentMethodId) {
        return paymentMehodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new PaymentMethodNotFoundException(paymentMethodId));
    }
}
