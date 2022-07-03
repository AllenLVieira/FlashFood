package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {OrderFlowService.class})
@ExtendWith(SpringExtension.class)
class OrderFlowServiceTest {
    @Autowired
    private OrderFlowService underTest;

    @MockBean
    private OrderRegistrationService orderRegistrationService;

    State state;
    City city;
    Address address;
    PaymentMethod paymentMethod;
    Cuisine cuisine;
    Restaurant restaurant;
    User user;
    DeliveryOrder deliveryOrder;

    @BeforeEach
    void setUp() {
        state = new State();
        state.setId(123L);
        state.setName("Allen");

        city = new City();
        city.setId(123L);
        city.setName("Allen");
        city.setState(state);

        address = new Address();
        address.setCity(city);
        address.setComplement("Complement");
        address.setDistrict("District");
        address.setNumber("42");
        address.setStreet("Street");
        address.setZipCode("21654");

        paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("Description");
        paymentMethod.setId(123L);

        cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Allen");
        cuisine.setRestaurant(new ArrayList<>());

        restaurant = new Restaurant();
        restaurant.setActive(true);
        restaurant.setAddress(address);
        restaurant.setCuisine(cuisine);
        restaurant.setFreightRate(BigDecimal.valueOf(42L));
        restaurant.setId(123L);
        restaurant.setManagers(new HashSet<>());
        restaurant.setName("Allen");
        restaurant.setOpenStatus(true);
        restaurant.setPaymentMethod(new HashSet<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(null);
        restaurant.setUpdateDate(null);

        user = new User();
        user.setEmail("allenvieira96@gmail.com");
        user.setGroups(new HashSet<>());
        user.setId(123L);
        user.setName("Allen");
        user.setPassword("123456");
        user.setRegistrationDate(null);

        deliveryOrder = new DeliveryOrder();
        deliveryOrder.setAmount(BigDecimal.valueOf(42L));
        deliveryOrder.setCancellationDate(null);
        deliveryOrder.setConfirmationDate(null);
        deliveryOrder.setDeliveryAddress(address);
        deliveryOrder.setDeliveryDate(null);
        deliveryOrder.setFreightRate(BigDecimal.valueOf(42L));
        deliveryOrder.setId(123L);
        deliveryOrder.setItems(new ArrayList<>());
        deliveryOrder.setOrderCode("123-456");
        deliveryOrder.setPaymentMethod(paymentMethod);
        deliveryOrder.setRegistrationDate(OffsetDateTime.now());
        deliveryOrder.setRestaurant(restaurant);
        deliveryOrder.setSubtotal(BigDecimal.valueOf(42L));
        deliveryOrder.setUser(user);
    }

    /**
     * Method under test: {@link OrderFlowService#confirmOrder(String)}
     */
    @Test
    void shouldConfirmOrder() {
        when(orderRegistrationService.findOrderOrElseThrow(any())).thenReturn(deliveryOrder);
        underTest.confirmOrder("123-456");
        verify(orderRegistrationService).findOrderOrElseThrow(any());
        assertSame(OrderStatus.CONFIRMED, deliveryOrder.getStatus());
    }

    /**
     * Method under test: {@link OrderFlowService#deliverOrder(String)}
     */
    @Test
    void testDeliverOrder() {
        deliveryOrder.setStatus(OrderStatus.CONFIRMED);
        when(orderRegistrationService.findOrderOrElseThrow(any())).thenReturn(deliveryOrder);
        underTest.deliverOrder("123-456");
        verify(orderRegistrationService).findOrderOrElseThrow(any());
        assertSame(OrderStatus.DELIVERED, deliveryOrder.getStatus());
    }

    /**
     * Method under test: {@link OrderFlowService#cancelOrder(String)}
     */
    @Test
    void testCancelOrder() {
        when(orderRegistrationService.findOrderOrElseThrow(any())).thenReturn(deliveryOrder);
        underTest.cancelOrder("123-456");
        verify(orderRegistrationService).findOrderOrElseThrow(any());
        assertSame(OrderStatus.CANCELED, deliveryOrder.getStatus());
    }
}

