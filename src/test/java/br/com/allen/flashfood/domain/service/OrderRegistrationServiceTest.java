package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.OrderNotFoundException;
import br.com.allen.flashfood.domain.model.*;
import br.com.allen.flashfood.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {OrderRegistrationService.class})
@ExtendWith(SpringExtension.class)
class OrderRegistrationServiceTest {

    @Autowired
    private OrderRegistrationService underTest;

    @MockBean
    private CityRegistrationService cityRegistrationService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private PaymentMethodRegistrationService paymentMethodRegistrationService;

    @MockBean
    private ProductRegistrationService productRegistrationService;

    @MockBean
    private RestaurantRegistrationService restaurantRegistrationService;

    @MockBean
    private UserRegistrationsService userRegistrationsService;

    State state;
    City city;
    Address address;
    Cuisine cuisine;
    Restaurant restaurant;
    User user;
    PaymentMethod paymentMethod;
    DeliveryOrder deliveryOrder;
    Product product;

    @BeforeEach
    void setUp() {
        state = new State();
        state.setId(123L);
        state.setName("State");

        city = new City();
        city.setId(123L);
        city.setName("City");
        city.setState(state);

        address = new Address();
        address.setCity(city);
        address.setComplement("Complement");
        address.setDistrict("District");
        address.setNumber("42");
        address.setStreet("Street");
        address.setZipCode("21654");

        cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Cuisine");
        cuisine.setRestaurant(new ArrayList<>());

        restaurant = new Restaurant();
        restaurant.setActive(true);
        restaurant.setAddress(address);
        restaurant.setCuisine(cuisine);
        restaurant.setFreightRate(BigDecimal.valueOf(42L));
        restaurant.setId(123L);
        restaurant.setManagers(new HashSet<>());
        restaurant.setName("Restaurant");
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
        user.setPassword("password");
        user.setRegistrationDate(null);
        when(userRegistrationsService.findUserOrElseThrow(any())).thenReturn(user);

        paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("Description");
        paymentMethod.setId(123L);

        deliveryOrder = new DeliveryOrder();
        deliveryOrder.setAmount(BigDecimal.valueOf(42L));
        deliveryOrder.setCancellationDate(null);
        deliveryOrder.setConfirmationDate(null);
        deliveryOrder.setDeliveryAddress(address);
        deliveryOrder.setDeliveryDate(null);
        deliveryOrder.setFreightRate(BigDecimal.valueOf(42L));
        deliveryOrder.setId(123L);
        deliveryOrder.setPaymentMethod(paymentMethod);
        deliveryOrder.setRegistrationDate(null);
        deliveryOrder.setRestaurant(restaurant);
        deliveryOrder.setSubtotal(BigDecimal.valueOf(42L));
        deliveryOrder.setUser(user);

        product = new Product();
        product.setId(123L);
        product.setName("Product");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setRestaurant(restaurant);
        product.setDescription("Description");
        product.setActive(true);
        restaurant.getProducts().add(product);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(123L);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setUnitPrice(BigDecimal.valueOf(42L));
        orderItem.setNote("");
        orderItem.setOrder(deliveryOrder);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        deliveryOrder.setItems(orderItems);
    }

    /**
     * Method under test: {@link OrderRegistrationService#createOrder(DeliveryOrder)}
     */
    @Test
    void shoudlThrowBusinessExceptionWhenRestaurantDoesNotAcceptPayment() {
        when(restaurantRegistrationService.findRestaurantOrElseThrow(any())).thenReturn(restaurant);
        when(cityRegistrationService.findCityOrElseThrow(any())).thenReturn(city);
        when(paymentMethodRegistrationService.findPaymentMethodOrElseThrow(any())).thenReturn(paymentMethod);

        assertThrows(BusinessException.class, () -> underTest.createOrder(deliveryOrder));
        verify(restaurantRegistrationService).findRestaurantOrElseThrow(any());
        verify(cityRegistrationService).findCityOrElseThrow(any());
        verify(userRegistrationsService).findUserOrElseThrow(any());
        verify(paymentMethodRegistrationService).findPaymentMethodOrElseThrow(any());
    }

    /**
     * Method under test: {@link OrderRegistrationService#findOrderOrElseThrow(String)}
     */
    @Test
    void shouldFindDeliveryOrderById() {
        Optional<DeliveryOrder> ofResult = Optional.of(deliveryOrder);
        when(orderRepository.findByOrderCode(any())).thenReturn(ofResult);
        DeliveryOrder actualDeliveryOrderOrElseThrowResult = underTest.findOrderOrElseThrow("123-456");
        verify(orderRepository).findByOrderCode(any());
        assertEquals("123", actualDeliveryOrderOrElseThrowResult.getId().toString());
    }

    /**
     * Method under test: {@link OrderRegistrationService#findOrderOrElseThrow(String)}
     */
    @Test
    void shouldThrowOrderNotFoundExceptionWhenTryToFindByOrderCode() {
        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> underTest.findOrderOrElseThrow("123-456"));
    }

    /**
     * Method under test: {@link OrderRegistrationService#createOrder(DeliveryOrder)}
     */
    @Test
    void shoudlThrowBusinessExceptionWhenPaymentMethodNotFound() {

        when(restaurantRegistrationService.findRestaurantOrElseThrow(any())).thenReturn(restaurant);
        when(cityRegistrationService.findCityOrElseThrow(any())).thenReturn(city);
        when(userRegistrationsService.findUserOrElseThrow(any())).thenReturn(user);

        when(paymentMethodRegistrationService.findPaymentMethodOrElseThrow(any()))
                .thenThrow(new BusinessException("An error occurred"));

        assertThrows(BusinessException.class, () -> underTest.createOrder(deliveryOrder));
        verify(restaurantRegistrationService).findRestaurantOrElseThrow(any());
        verify(cityRegistrationService).findCityOrElseThrow(any());
        verify(userRegistrationsService).findUserOrElseThrow(any());
        verify(paymentMethodRegistrationService).findPaymentMethodOrElseThrow(any());
    }


    /**
     * Method under test: {@link OrderRegistrationService#createOrder(DeliveryOrder)}
     */
    @Test
    void shouldSaveSuccessfullyNewOrder() {
        restaurant.getPaymentMethod().add(paymentMethod);
        when(orderRepository.save(any())).thenReturn(deliveryOrder);
        when(restaurantRegistrationService.findRestaurantOrElseThrow(any())).thenReturn(restaurant);
        when(cityRegistrationService.findCityOrElseThrow(any())).thenReturn(city);
        when(userRegistrationsService.findUserOrElseThrow(any())).thenReturn(user);
        when(paymentMethodRegistrationService.findPaymentMethodOrElseThrow(any())).thenReturn(paymentMethod);
        when(productRegistrationService.findProductOrElseThrow(any(), any())).thenReturn(product);

        DeliveryOrder actualCreateOrderResult = underTest.createOrder(deliveryOrder);
        assertSame(deliveryOrder, actualCreateOrderResult);
        assertEquals("84", actualCreateOrderResult.getAmount().toString());
        assertEquals("42", actualCreateOrderResult.getFreightRate().toString());
        verify(orderRepository).save(any());
        verify(restaurantRegistrationService).findRestaurantOrElseThrow(any());
        verify(cityRegistrationService).findCityOrElseThrow(any());
        verify(userRegistrationsService).findUserOrElseThrow(any());
        verify(paymentMethodRegistrationService).findPaymentMethodOrElseThrow(any());
    }
}

