package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.model.*;
import br.com.allen.flashfood.domain.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

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

    /**
     * Method under test: {@link OrderRegistrationService#createOrder(DeliveryOrder)}
     */
    @Test
    void shoudlThrowBusinessExceptionWhenRestaurantDoesNotAcceptPayment() {
        State state = new State();
        state.setId(123L);
        state.setName("State");

        City city = new City();
        city.setId(123L);
        city.setName("City");
        city.setState(state);

        Address address = new Address();
        address.setCity(city);
        address.setComplement("Complement");
        address.setDistrict("District");
        address.setNumber("42");
        address.setStreet("Street");
        address.setZipCode("21654");

        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Cuisine");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
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
        when(restaurantRegistrationService.findRestaurantOrElseThrow(any())).thenReturn(restaurant);

        State state1 = new State();
        state1.setId(123L);
        state1.setName("State");

        City city1 = new City();
        city1.setId(123L);
        city1.setName("City");
        city1.setState(state1);
        when(cityRegistrationService.findCityOrElseThrow(any())).thenReturn(city1);

        User user = new User();
        user.setEmail("allenvieira96@gmail.com");
        user.setGroups(new HashSet<>());
        user.setId(123L);
        user.setName("Allen");
        user.setPassword("password");
        user.setRegistrationDate(null);
        when(userRegistrationsService.findUserOrElseThrow(any())).thenReturn(user);

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("The characteristics of someone or something");
        paymentMethod.setId(123L);
        when(paymentMethodRegistrationService.findPaymentMethodOrElseThrow(any())).thenReturn(paymentMethod);

        State state2 = new State();
        state2.setId(123L);
        state2.setName("State");

        City city2 = new City();
        city2.setId(123L);
        city2.setName("City");
        city2.setState(state2);

        Address address1 = new Address();
        address1.setCity(city2);
        address1.setComplement("Complement");
        address1.setDistrict("District");
        address1.setNumber("42");
        address1.setStreet("Street");
        address1.setZipCode("21654");

        PaymentMethod paymentMethod1 = new PaymentMethod();
        paymentMethod1.setDescription("The characteristics of someone or something");
        paymentMethod1.setId(123L);

        City city3 = new City();
        city3.setId(123L);
        city3.setName("City");
        city3.setState(new State());

        Address address2 = new Address();
        address2.setCity(city3);
        address2.setComplement("Complement");
        address2.setDistrict("District");
        address2.setNumber("42");
        address2.setStreet("Street");
        address2.setZipCode("21654");

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Cuisine");
        cuisine1.setRestaurant(new ArrayList<>());

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setActive(true);
        restaurant1.setAddress(address2);
        restaurant1.setCuisine(cuisine1);
        restaurant1.setFreightRate(BigDecimal.valueOf(42L));
        restaurant1.setId(123L);
        restaurant1.setManagers(new HashSet<>());
        restaurant1.setName("Restaurant");
        restaurant1.setOpenStatus(true);
        restaurant1.setPaymentMethod(new HashSet<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(null);
        restaurant1.setUpdateDate(null);

        User user1 = new User();
        user1.setEmail("allenvieira96@gmail.com");
        user1.setGroups(new HashSet<>());
        user1.setId(123L);
        user1.setName("Allen");
        user1.setPassword("password");
        user1.setRegistrationDate(null);

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setAmount(BigDecimal.valueOf(42L));
        deliveryOrder.setCancellationDate(null);
        deliveryOrder.setConfirmationDate(null);
        deliveryOrder.setDeliveryAddress(address1);
        deliveryOrder.setDeliveryDate(null);
        deliveryOrder.setFreightRate(BigDecimal.valueOf(42L));
        deliveryOrder.setId(123L);
        deliveryOrder.setItems(new ArrayList<>());
        deliveryOrder.setPaymentMethod(paymentMethod1);
        deliveryOrder.setRegistrationDate(null);
        deliveryOrder.setRestaurant(restaurant1);
        deliveryOrder.setStatus(OrderStatus.CREATED);
        deliveryOrder.setSubtotal(BigDecimal.valueOf(42L));
        deliveryOrder.setUser(user1);
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
    void shoudlThrowBusinessExceptionWhenPaymentMethodNotFound() {
        State state = new State();
        state.setId(123L);
        state.setName("State");

        City city = new City();
        city.setId(123L);
        city.setName("City");
        city.setState(state);

        Address address = new Address();
        address.setCity(city);
        address.setComplement("Complement");
        address.setDistrict("District");
        address.setNumber("42");
        address.setStreet("Street");
        address.setZipCode("21654");

        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Cuisine");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
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
        when(restaurantRegistrationService.findRestaurantOrElseThrow(any())).thenReturn(restaurant);

        State state1 = new State();
        state1.setId(123L);
        state1.setName("State");

        City city1 = new City();
        city1.setId(123L);
        city1.setName("City");
        city1.setState(state1);
        when(cityRegistrationService.findCityOrElseThrow(any())).thenReturn(city1);

        User user = new User();
        user.setEmail("allenvieira96@gmail.com");
        user.setGroups(new HashSet<>());
        user.setId(123L);
        user.setName("Allen");
        user.setPassword("password");
        user.setRegistrationDate(null);
        when(userRegistrationsService.findUserOrElseThrow(any())).thenReturn(user);
        when(paymentMethodRegistrationService.findPaymentMethodOrElseThrow(any()))
                .thenThrow(new BusinessException("An error occurred"));

        State state2 = new State();
        state2.setId(123L);
        state2.setName("State");

        City city2 = new City();
        city2.setId(123L);
        city2.setName("City");
        city2.setState(state2);

        Address address1 = new Address();
        address1.setCity(city2);
        address1.setComplement("Complement");
        address1.setDistrict("District");
        address1.setNumber("42");
        address1.setStreet("Street");
        address1.setZipCode("21654");

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("The characteristics of someone or something");
        paymentMethod.setId(123L);

        City city3 = new City();
        city3.setId(123L);
        city3.setName("City");
        city3.setState(new State());

        Address address2 = new Address();
        address2.setCity(city3);
        address2.setComplement("Complement");
        address2.setDistrict("District");
        address2.setNumber("42");
        address2.setStreet("Street");
        address2.setZipCode("21654");

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Cuisine");
        cuisine1.setRestaurant(new ArrayList<>());

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setActive(true);
        restaurant1.setAddress(address2);
        restaurant1.setCuisine(cuisine1);
        restaurant1.setFreightRate(BigDecimal.valueOf(42L));
        restaurant1.setId(123L);
        restaurant1.setManagers(new HashSet<>());
        restaurant1.setName("Restaurant");
        restaurant1.setOpenStatus(true);
        restaurant1.setPaymentMethod(new HashSet<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(null);
        restaurant1.setUpdateDate(null);

        User user1 = new User();
        user1.setEmail("allenvieira96@gmail.com");
        user1.setGroups(new HashSet<>());
        user1.setId(123L);
        user1.setName("Allen");
        user1.setPassword("password");
        user1.setRegistrationDate(null);

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setAmount(BigDecimal.valueOf(42L));
        deliveryOrder.setCancellationDate(null);
        deliveryOrder.setConfirmationDate(null);
        deliveryOrder.setDeliveryAddress(address1);
        deliveryOrder.setDeliveryDate(null);
        deliveryOrder.setFreightRate(BigDecimal.valueOf(42L));
        deliveryOrder.setId(123L);
        deliveryOrder.setItems(new ArrayList<>());
        deliveryOrder.setPaymentMethod(paymentMethod);
        deliveryOrder.setRegistrationDate(null);
        deliveryOrder.setRestaurant(restaurant1);
        deliveryOrder.setStatus(OrderStatus.CREATED);
        deliveryOrder.setSubtotal(BigDecimal.valueOf(42L));
        deliveryOrder.setUser(user1);
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
        State state = new State();
        state.setId(123L);
        state.setName("State");

        City city = new City();
        city.setId(123L);
        city.setName("City");
        city.setState(state);

        Address address = new Address();
        address.setCity(city);
        address.setComplement("Complement");
        address.setDistrict("District");
        address.setNumber("42");
        address.setStreet("Street");
        address.setZipCode("21654");

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("The characteristics of someone or something");
        paymentMethod.setId(123L);

        State state1 = new State();
        state1.setId(123L);
        state1.setName("State");

        City city1 = new City();
        city1.setId(123L);
        city1.setName("City");
        city1.setState(state1);

        Address address1 = new Address();
        address1.setCity(city1);
        address1.setComplement("Complement");
        address1.setDistrict("District");
        address1.setNumber("42");
        address1.setStreet("Street");
        address1.setZipCode("21654");

        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Cuisine");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
        restaurant.setActive(true);
        restaurant.setAddress(address1);
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

        User user = new User();
        user.setEmail("allenvieira96@gmail.com");
        user.setGroups(new HashSet<>());
        user.setId(123L);
        user.setName("Allen");
        user.setPassword("password");
        user.setRegistrationDate(null);

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setAmount(BigDecimal.valueOf(42L));
        deliveryOrder.setCancellationDate(null);
        deliveryOrder.setConfirmationDate(null);
        deliveryOrder.setDeliveryAddress(address);
        deliveryOrder.setDeliveryDate(null);
        deliveryOrder.setFreightRate(BigDecimal.valueOf(42L));
        deliveryOrder.setId(123L);
        deliveryOrder.setItems(new ArrayList<>());
        deliveryOrder.setPaymentMethod(paymentMethod);
        deliveryOrder.setRegistrationDate(null);
        deliveryOrder.setRestaurant(restaurant);
        deliveryOrder.setStatus(OrderStatus.CREATED);
        deliveryOrder.setSubtotal(BigDecimal.valueOf(42L));
        deliveryOrder.setUser(user);
        when(orderRepository.save(any())).thenReturn(deliveryOrder);

        State state2 = new State();
        state2.setId(123L);
        state2.setName("State");

        City city2 = new City();
        city2.setId(123L);
        city2.setName("City");
        city2.setState(state2);

        Address address2 = new Address();
        address2.setCity(city2);
        address2.setComplement("Complement");
        address2.setDistrict("District");
        address2.setNumber("42");
        address2.setStreet("Street");
        address2.setZipCode("21654");

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Cuisine");
        cuisine1.setRestaurant(new ArrayList<>());
        Restaurant restaurant1 = mock(Restaurant.class);
        when(restaurant1.doesNotAcceptPaymentMethod(any())).thenReturn(false);
        when(restaurant1.getFreightRate()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(restaurant1).setActive(any());
        doNothing().when(restaurant1).setAddress(any());
        doNothing().when(restaurant1).setCuisine(any());
        doNothing().when(restaurant1).setFreightRate(any());
        doNothing().when(restaurant1).setId(any());
        doNothing().when(restaurant1).setManagers(any());
        doNothing().when(restaurant1).setName(any());
        doNothing().when(restaurant1).setOpenStatus(any());
        doNothing().when(restaurant1).setPaymentMethod(any());
        doNothing().when(restaurant1).setProducts(any());
        doNothing().when(restaurant1).setRegistrationDate(any());
        doNothing().when(restaurant1).setUpdateDate(any());
        restaurant1.setActive(true);
        restaurant1.setAddress(address2);
        restaurant1.setCuisine(cuisine1);
        restaurant1.setFreightRate(BigDecimal.valueOf(42L));
        restaurant1.setId(123L);
        restaurant1.setManagers(new HashSet<>());
        restaurant1.setName("Restaurant");
        restaurant1.setOpenStatus(true);
        restaurant1.setPaymentMethod(new HashSet<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(null);
        restaurant1.setUpdateDate(null);
        when(restaurantRegistrationService.findRestaurantOrElseThrow(any())).thenReturn(restaurant1);

        State state3 = new State();
        state3.setId(123L);
        state3.setName("State");

        City city3 = new City();
        city3.setId(123L);
        city3.setName("City");
        city3.setState(state3);
        when(cityRegistrationService.findCityOrElseThrow(any())).thenReturn(city3);

        User user1 = new User();
        user1.setEmail("allenvieira96@gmail.com");
        user1.setGroups(new HashSet<>());
        user1.setId(123L);
        user1.setName("Allen");
        user1.setPassword("password");
        user1.setRegistrationDate(null);
        when(userRegistrationsService.findUserOrElseThrow(any())).thenReturn(user1);

        PaymentMethod paymentMethod1 = new PaymentMethod();
        paymentMethod1.setDescription("The characteristics of someone or something");
        paymentMethod1.setId(123L);
        when(paymentMethodRegistrationService.findPaymentMethodOrElseThrow(any())).thenReturn(paymentMethod1);

        State state4 = new State();
        state4.setId(123L);
        state4.setName("State");

        City city4 = new City();
        city4.setId(123L);
        city4.setName("City");
        city4.setState(state4);

        Address address3 = new Address();
        address3.setCity(city4);
        address3.setComplement("Complement");
        address3.setDistrict("District");
        address3.setNumber("42");
        address3.setStreet("Street");
        address3.setZipCode("21654");

        PaymentMethod paymentMethod2 = new PaymentMethod();
        paymentMethod2.setDescription("The characteristics of someone or something");
        paymentMethod2.setId(123L);

        City city5 = new City();
        city5.setId(123L);
        city5.setName("City");
        city5.setState(new State());

        Address address4 = new Address();
        address4.setCity(city5);
        address4.setComplement("Complement");
        address4.setDistrict("District");
        address4.setNumber("42");
        address4.setStreet("Street");
        address4.setZipCode("21654");

        Cuisine cuisine2 = new Cuisine();
        cuisine2.setId(123L);
        cuisine2.setName("Cuisine");
        cuisine2.setRestaurant(new ArrayList<>());

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setActive(true);
        restaurant2.setAddress(address4);
        restaurant2.setCuisine(cuisine2);
        restaurant2.setFreightRate(BigDecimal.valueOf(42L));
        restaurant2.setId(123L);
        restaurant2.setManagers(new HashSet<>());
        restaurant2.setName("Restaurant");
        restaurant2.setOpenStatus(true);
        restaurant2.setPaymentMethod(new HashSet<>());
        restaurant2.setProducts(new ArrayList<>());
        restaurant2.setRegistrationDate(null);
        restaurant2.setUpdateDate(null);

        User user2 = new User();
        user2.setEmail("allenvieira96@gmail.com");
        user2.setGroups(new HashSet<>());
        user2.setId(123L);
        user2.setName("Allen");
        user2.setPassword("password");
        user2.setRegistrationDate(null);

        DeliveryOrder deliveryOrder1 = new DeliveryOrder();
        deliveryOrder1.setAmount(BigDecimal.valueOf(42L));
        deliveryOrder1.setCancellationDate(null);
        deliveryOrder1.setConfirmationDate(null);
        deliveryOrder1.setDeliveryAddress(address3);
        deliveryOrder1.setDeliveryDate(null);
        deliveryOrder1.setFreightRate(BigDecimal.valueOf(42L));
        deliveryOrder1.setId(123L);
        deliveryOrder1.setItems(new ArrayList<>());
        deliveryOrder1.setPaymentMethod(paymentMethod2);
        deliveryOrder1.setRegistrationDate(null);
        deliveryOrder1.setRestaurant(restaurant2);
        deliveryOrder1.setStatus(OrderStatus.CREATED);
        deliveryOrder1.setSubtotal(BigDecimal.valueOf(42L));
        deliveryOrder1.setUser(user2);
        DeliveryOrder actualCreateOrderResult = underTest.createOrder(deliveryOrder1);
        assertSame(deliveryOrder, actualCreateOrderResult);
        assertEquals("42", actualCreateOrderResult.getAmount().toString());
        assertEquals("42", actualCreateOrderResult.getFreightRate().toString());
        verify(orderRepository).save(any());
        verify(restaurantRegistrationService).findRestaurantOrElseThrow(any());
        verify(restaurant1).doesNotAcceptPaymentMethod(any());
        verify(restaurant1).getFreightRate();
        verify(restaurant1).setActive(any());
        verify(restaurant1).setAddress(any());
        verify(restaurant1).setCuisine(any());
        verify(restaurant1).setFreightRate(any());
        verify(restaurant1).setId(any());
        verify(restaurant1).setManagers(any());
        verify(restaurant1).setName(any());
        verify(restaurant1).setOpenStatus(any());
        verify(restaurant1).setPaymentMethod(any());
        verify(restaurant1).setProducts(any());
        verify(restaurant1).setRegistrationDate(any());
        verify(restaurant1).setUpdateDate(any());
        verify(cityRegistrationService).findCityOrElseThrow(any());
        verify(userRegistrationsService).findUserOrElseThrow(any());
        verify(paymentMethodRegistrationService).findPaymentMethodOrElseThrow(any());
    }
}

