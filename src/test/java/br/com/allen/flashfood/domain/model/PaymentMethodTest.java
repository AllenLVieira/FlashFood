package br.com.allen.flashfood.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PaymentMethodTest {
    @Test
    void testCanEqual() {
        assertFalse((new PaymentMethod()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        PaymentMethod paymentMethod = new PaymentMethod();

        PaymentMethod paymentMethod1 = new PaymentMethod();
        paymentMethod1.setDescription("The characteristics of someone or something");
        paymentMethod1.setId(123L);
        assertTrue(paymentMethod.canEqual(paymentMethod1));
    }

    @Test
    void testConstructor() {
        PaymentMethod actualPaymentMethod = new PaymentMethod();
        actualPaymentMethod.setDescription("The characteristics of someone or something");
        actualPaymentMethod.setId(123L);
        assertEquals("The characteristics of someone or something", actualPaymentMethod.getDescription());
        assertEquals(123L, actualPaymentMethod.getId().longValue());
        assertEquals("PaymentMethod(id=123, description=The characteristics of someone or something)",
                actualPaymentMethod.toString());
    }

    @Test
    void testEquals() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("The characteristics of someone or something");
        paymentMethod.setId(123L);
        assertFalse(paymentMethod.equals(null));
    }

    @Test
    void testEquals2() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("The characteristics of someone or something");
        paymentMethod.setId(123L);
        assertFalse(paymentMethod.equals("Different type to PaymentMethod"));
    }

    @Test
    void testEquals3() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("The characteristics of someone or something");
        paymentMethod.setId(123L);
        assertTrue(paymentMethod.equals(paymentMethod));
        int expectedHashCodeResult = paymentMethod.hashCode();
        assertEquals(expectedHashCodeResult, paymentMethod.hashCode());
    }

    @Test
    void testEquals4() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("The characteristics of someone or something");
        paymentMethod.setId(123L);

        PaymentMethod paymentMethod1 = new PaymentMethod();
        paymentMethod1.setDescription("The characteristics of someone or something");
        paymentMethod1.setId(123L);
        assertTrue(paymentMethod.equals(paymentMethod1));
        int expectedHashCodeResult = paymentMethod.hashCode();
        assertEquals(expectedHashCodeResult, paymentMethod1.hashCode());
    }

    @Test
    void testEquals5() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("The characteristics of someone or something");
        paymentMethod.setId(1L);

        PaymentMethod paymentMethod1 = new PaymentMethod();
        paymentMethod1.setDescription("The characteristics of someone or something");
        paymentMethod1.setId(123L);
        assertFalse(paymentMethod.equals(paymentMethod1));
    }

    @Test
    void testEquals6() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("The characteristics of someone or something");
        paymentMethod.setId(null);

        PaymentMethod paymentMethod1 = new PaymentMethod();
        paymentMethod1.setDescription("The characteristics of someone or something");
        paymentMethod1.setId(123L);
        assertFalse(paymentMethod.equals(paymentMethod1));
    }

    @Test
    void testEquals7() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("The characteristics of someone or something");
        paymentMethod.setId(null);

        PaymentMethod paymentMethod1 = new PaymentMethod();
        paymentMethod1.setDescription("The characteristics of someone or something");
        paymentMethod1.setId(null);
        assertTrue(paymentMethod.equals(paymentMethod1));
        int expectedHashCodeResult = paymentMethod.hashCode();
        assertEquals(expectedHashCodeResult, paymentMethod1.hashCode());
    }
}

