package br.com.allen.flashfood.infrastructure.repository;

import br.com.allen.flashfood.domain.model.PaymentMethod;
import br.com.allen.flashfood.domain.repository.PaymentMehodRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class PaymentMethodRepositoryJpa implements PaymentMehodRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PaymentMethod> getAllPaymentsMethods() {
        return entityManager.createQuery("from PaymentMethod", PaymentMethod.class)
                .getResultList();
    }

    @Override
    public PaymentMethod getPaymentMethodById(Long id) {
        return entityManager.find(PaymentMethod.class, id);
    }

    @Transactional
    @Override
    public PaymentMethod addPaymentMethod(PaymentMethod paymentMethod) {
        return entityManager.merge(paymentMethod);
    }

    @Transactional
    @Override
    public void removePaymentMethod(PaymentMethod paymentMethod) {
        paymentMethod = getPaymentMethodById(paymentMethod.getId());
        entityManager.remove(paymentMethod);
    }
}
