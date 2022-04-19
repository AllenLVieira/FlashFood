package br.com.allen.flashfood.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderStatusTest {

    @Test
    void testValues() {
        OrderStatus[] actualValuesResult = OrderStatus.values();
        assertEquals(4, actualValuesResult.length);
        assertEquals(OrderStatus.CREATED, actualValuesResult[0]);
        assertEquals(OrderStatus.CONFIRMED, actualValuesResult[1]);
        assertEquals(OrderStatus.DELIVERED, actualValuesResult[2]);
        assertEquals(OrderStatus.CANCELED, actualValuesResult[3]);
    }
}

