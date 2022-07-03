package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.PaymentMethodNotFoundException;
import br.com.allen.flashfood.domain.model.PaymentMethod;
import br.com.allen.flashfood.domain.repository.PaymentMehodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PaymentMethodRegistrationService.class})
@ExtendWith(SpringExtension.class)
class PaymentMethodRegistrationServiceTest {
    @Autowired
    private PaymentMethodRegistrationService underTest;

    @MockBean
    private PaymentMehodRepository paymentMehodRepository;

    PaymentMethod paymentMethod;

    @BeforeEach
    void setUp() {
        paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("Description");
        paymentMethod.setId(123L);
    }

    /**
     * Method under test: {@link PaymentMethodRegistrationService#savePaymentMethod(PaymentMethod)}
     */
    @Test
    void shouldSavePaymentMethod() {
        when(paymentMehodRepository.save(any())).thenReturn(paymentMethod);

        PaymentMethod newPaymentMethod = new PaymentMethod();
        newPaymentMethod.setDescription("Description");
        newPaymentMethod.setId(123L);
        assertSame(paymentMethod, underTest.savePaymentMethod(newPaymentMethod));
        verify(paymentMehodRepository).save(any());
    }

    /**
     * Method under test: {@link PaymentMethodRegistrationService#savePaymentMethod(PaymentMethod)}
     */
    @Test
    void shouldThrowsEmptyResultDataAccessExceptionWhenSavePayment() {
        when(paymentMehodRepository.save(any())).thenThrow(new EmptyResultDataAccessException(3));

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("Description");
        paymentMethod.setId(123L);
        assertThrows(EmptyResultDataAccessException.class,
                () -> underTest.savePaymentMethod(paymentMethod));
        verify(paymentMehodRepository).save(any());
    }

    /**
     * Method under test: {@link PaymentMethodRegistrationService#deletePaymentMethod(Long)}
     */
    @Test
    void shouldDeletePaymentMethod() {
        doNothing().when(paymentMehodRepository).flush();
        doNothing().when(paymentMehodRepository).deleteById(any());
        underTest.deletePaymentMethod(123L);
        verify(paymentMehodRepository).flush();
        verify(paymentMehodRepository).deleteById(any());
    }

    /**
     * Method under test: {@link PaymentMethodRegistrationService#deletePaymentMethod(Long)}
     */
    @Test
    void shouldThrowsPaymentMethodNotFoundExceptionWhenDeletePaymentMethod() {
        doThrow(new EmptyResultDataAccessException(3)).when(paymentMehodRepository).deleteById(any());
        assertThrows(PaymentMethodNotFoundException.class, () -> underTest.deletePaymentMethod(123L));
        verify(paymentMehodRepository).deleteById(any());
    }

    /**
     * Method under test: {@link PaymentMethodRegistrationService#deletePaymentMethod(Long)}
     */
    @Test
    void shouldThrowsEntityInUseExceptionWhenDeletePaymentMethod() {
        doThrow(new DataIntegrityViolationException("An error occured")).when(paymentMehodRepository).deleteById(any());
        assertThrows(EntityInUseException.class, () -> underTest.deletePaymentMethod(123L));
        verify(paymentMehodRepository).deleteById(any());
    }

    /**
     * Method under test: {@link PaymentMethodRegistrationService#findPaymentMethodOrElseThrow(Long)}
     */
    @Test
    void shouldFindPaymentMethod() {
        Optional<PaymentMethod> ofResult = Optional.of(paymentMethod);
        when(paymentMehodRepository.findById(any())).thenReturn(ofResult);
        assertSame(paymentMethod, underTest.findPaymentMethodOrElseThrow(123L));
        verify(paymentMehodRepository).findById(any());
    }

    /**
     * Method under test: {@link PaymentMethodRegistrationService#findPaymentMethodOrElseThrow(Long)}
     */
    @Test
    void shouldThrowPaymentMethodNotFoundExceptionWhenPaymentMethodIsEmpty() {
        when(paymentMehodRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(PaymentMethodNotFoundException.class,
                () -> underTest.findPaymentMethodOrElseThrow(123L));
        verify(paymentMehodRepository).findById(any());
    }

    /**
     * Method under test: {@link PaymentMethodRegistrationService#findPaymentMethodOrElseThrow(Long)}
     */
    @Test
    void shouldThrowPaymentMethodNotFoundExceptionWhenPaymentMethodDoesNotExists() {
        when(paymentMehodRepository.findById(any())).thenThrow(new EmptyResultDataAccessException(3));
        assertThrows(EmptyResultDataAccessException.class,
                () -> underTest.findPaymentMethodOrElseThrow(123L));
        verify(paymentMehodRepository).findById(any());
    }
}

