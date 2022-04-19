package br.com.allen.flashfood.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class DeliveryOrderTest {
    @Test
    void testCanEqual() {
        assertFalse((new DeliveryOrder()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        DeliveryOrder deliveryOrder = new DeliveryOrder();

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setGroups(new ArrayList<>());
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRegistrationDate(LocalDateTime.of(3, 3, 3, 3, 1));

        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city = new City();
        city.setId(123L);
        city.setName("Name");
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
        state1.setName("Name");

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
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
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address1);
        restaurant.setCuisine(cuisine);
        restaurant.setFreightRate(BigDecimal.valueOf(42L));
        restaurant.setId(123L);
        restaurant.setName("Name");
        restaurant.setPaymentMethod(new ArrayList<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(null);
        restaurant.setUpdateDate(null);

        DeliveryOrder deliveryOrder1 = new DeliveryOrder();
        deliveryOrder1.setAmount(BigDecimal.valueOf(42L));
        deliveryOrder1.setCancellationDate(LocalDateTime.of(3, 3, 3, 3, 1));
        deliveryOrder1.setCliente(user);
        deliveryOrder1.setConfirmationDate(LocalDateTime.of(3, 3, 3, 3, 1));
        deliveryOrder1.setDeliveryAddress(address);
        deliveryOrder1.setDeliveryDate(LocalDateTime.of(3, 3, 3, 3, 1));
        deliveryOrder1.setFreightRate(BigDecimal.valueOf(42L));
        deliveryOrder1.setId(123L);
        deliveryOrder1.setItems(new ArrayList<>());
        deliveryOrder1.setPaymentMethod(paymentMethod);
        deliveryOrder1.setRegistrationDate(LocalDateTime.of(3, 3, 3, 3, 1));
        deliveryOrder1.setRestaurant(restaurant);
        deliveryOrder1.setStatus(OrderStatus.CREATED);
        deliveryOrder1.setSubtotal(BigDecimal.valueOf(42L));
        assertTrue(deliveryOrder.canEqual(deliveryOrder1));
    }

    @Test
    void testConstructor() {
        DeliveryOrder actualDeliveryOrder = new DeliveryOrder();
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        actualDeliveryOrder.setAmount(valueOfResult);
        LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
        actualDeliveryOrder.setCancellationDate(ofResult);
        User user = new User();
        user.setEmail("jane.doe@example.org");
        ArrayList<Family> familyList = new ArrayList<>();
        user.setGroups(familyList);
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        actualDeliveryOrder.setCliente(user);
        LocalDateTime ofResult1 = LocalDateTime.of(1, 1, 1, 1, 1);
        actualDeliveryOrder.setConfirmationDate(ofResult1);
        State state = new State();
        state.setId(123L);
        state.setName("Name");
        City city = new City();
        city.setId(123L);
        city.setName("Name");
        city.setState(state);
        Address address = new Address();
        address.setCity(city);
        address.setComplement("Complement");
        address.setDistrict("District");
        address.setNumber("42");
        address.setStreet("Street");
        address.setZipCode("21654");
        actualDeliveryOrder.setDeliveryAddress(address);
        LocalDateTime ofResult2 = LocalDateTime.of(1, 1, 1, 1, 1);
        actualDeliveryOrder.setDeliveryDate(ofResult2);
        BigDecimal valueOfResult1 = BigDecimal.valueOf(42L);
        actualDeliveryOrder.setFreightRate(valueOfResult1);
        actualDeliveryOrder.setId(123L);
        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        actualDeliveryOrder.setItems(orderItemList);
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("The characteristics of someone or something");
        paymentMethod.setId(123L);
        actualDeliveryOrder.setPaymentMethod(paymentMethod);
        LocalDateTime ofResult3 = LocalDateTime.of(1, 1, 1, 1, 1);
        actualDeliveryOrder.setRegistrationDate(ofResult3);
        State state1 = new State();
        state1.setId(123L);
        state1.setName("Name");
        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
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
        cuisine.setName("Name");
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        cuisine.setRestaurant(restaurantList);
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address1);
        restaurant.setCuisine(cuisine);
        BigDecimal valueOfResult2 = BigDecimal.valueOf(42L);
        restaurant.setFreightRate(valueOfResult2);
        restaurant.setId(123L);
        restaurant.setName("Name");
        ArrayList<PaymentMethod> paymentMethodList = new ArrayList<>();
        restaurant.setPaymentMethod(paymentMethodList);
        ArrayList<Product> productList = new ArrayList<>();
        restaurant.setProducts(productList);
        restaurant.setRegistrationDate(null);
        restaurant.setUpdateDate(null);
        actualDeliveryOrder.setRestaurant(restaurant);
        actualDeliveryOrder.setStatus(OrderStatus.CREATED);
        BigDecimal valueOfResult3 = BigDecimal.valueOf(42L);
        actualDeliveryOrder.setSubtotal(valueOfResult3);
        BigDecimal amount = actualDeliveryOrder.getAmount();
        assertSame(valueOfResult, amount);
        assertEquals(valueOfResult1, amount);
        assertEquals(valueOfResult2, amount);
        assertEquals(valueOfResult3, amount);
        assertSame(ofResult, actualDeliveryOrder.getCancellationDate());
        assertSame(user, actualDeliveryOrder.getCliente());
        assertSame(ofResult1, actualDeliveryOrder.getConfirmationDate());
        Address deliveryAddress = actualDeliveryOrder.getDeliveryAddress();
        assertSame(address, deliveryAddress);
        assertEquals(address1, deliveryAddress);
        assertSame(ofResult2, actualDeliveryOrder.getDeliveryDate());
        BigDecimal freightRate = actualDeliveryOrder.getFreightRate();
        assertSame(valueOfResult1, freightRate);
        assertEquals(valueOfResult2, freightRate);
        assertEquals(amount, freightRate);
        BigDecimal subtotal = actualDeliveryOrder.getSubtotal();
        assertEquals(subtotal, freightRate);
        assertEquals(123L, actualDeliveryOrder.getId().longValue());
        List<OrderItem> items = actualDeliveryOrder.getItems();
        assertSame(orderItemList, items);
        assertEquals(familyList, items);
        assertEquals(restaurantList, items);
        assertEquals(paymentMethodList, items);
        assertEquals(productList, items);
        assertSame(paymentMethod, actualDeliveryOrder.getPaymentMethod());
        assertSame(ofResult3, actualDeliveryOrder.getRegistrationDate());
        assertSame(restaurant, actualDeliveryOrder.getRestaurant());
        assertEquals(OrderStatus.CREATED, actualDeliveryOrder.getStatus());
        assertSame(valueOfResult3, subtotal);
        assertEquals(valueOfResult1, subtotal);
        assertEquals(valueOfResult2, subtotal);
        assertEquals(amount, subtotal);
        assertEquals("DeliveryOrder(id=123, subtotal=42, freightRate=42, amount=42, deliveryAddress=Address(zipCode=21654,"
                + " street=Street, number=42, city=City(id=123, name=Name, state=State(id=123, name=Name)), district=District,"
                + " complement=Complement), status=CREATED, registrationDate=0001-01-01T01:01, confirmationDate=0001-01"
                + "-01T01:01, cancellationDate=0001-01-01T01:01, deliveryDate=0001-01-01T01:01, paymentMethod=PaymentMethod"
                + "(id=123, description=The characteristics of someone or something), restaurant=Restaurant(id=123,"
                + " name=Name, freightRate=42, address=Address(zipCode=21654, street=Street, number=42, city=City(id=123,"
                + " name=Name, state=State(id=123, name=Name)), district=District, complement=Complement), registrationDate"
                + "=null, updateDate=null, cuisine=Cuisine(id=123, name=Name, restaurant=[]), products=[], paymentMethod=[]),"
                + " cliente=User(id=123, name=Name, email=jane.doe@example.org, password=iloveyou, registrationDate=0001"
                + "-01-01T01:01, groups=[]), items=[])", actualDeliveryOrder.toString());
    }

    @Test
    void testEquals() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setGroups(new ArrayList<>());
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));

        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city = new City();
        city.setId(123L);
        city.setName("Name");
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
        state1.setName("Name");

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
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
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address1);
        restaurant.setCuisine(cuisine);
        restaurant.setFreightRate(BigDecimal.valueOf(42L));
        restaurant.setId(123L);
        restaurant.setName("Name");
        restaurant.setPaymentMethod(new ArrayList<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(null);
        restaurant.setUpdateDate(null);

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setAmount(BigDecimal.valueOf(42L));
        deliveryOrder.setCancellationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setCliente(user);
        deliveryOrder.setConfirmationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setDeliveryAddress(address);
        deliveryOrder.setDeliveryDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setFreightRate(BigDecimal.valueOf(42L));
        deliveryOrder.setId(123L);
        deliveryOrder.setItems(new ArrayList<>());
        deliveryOrder.setPaymentMethod(paymentMethod);
        deliveryOrder.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setRestaurant(restaurant);
        deliveryOrder.setStatus(OrderStatus.CREATED);
        deliveryOrder.setSubtotal(BigDecimal.valueOf(42L));
        assertFalse(deliveryOrder.equals(null));
    }

    @Test
    void testEquals2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setGroups(new ArrayList<>());
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));

        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city = new City();
        city.setId(123L);
        city.setName("Name");
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
        state1.setName("Name");

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
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
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address1);
        restaurant.setCuisine(cuisine);
        restaurant.setFreightRate(BigDecimal.valueOf(42L));
        restaurant.setId(123L);
        restaurant.setName("Name");
        restaurant.setPaymentMethod(new ArrayList<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(null);
        restaurant.setUpdateDate(null);

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setAmount(BigDecimal.valueOf(42L));
        deliveryOrder.setCancellationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setCliente(user);
        deliveryOrder.setConfirmationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setDeliveryAddress(address);
        deliveryOrder.setDeliveryDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setFreightRate(BigDecimal.valueOf(42L));
        deliveryOrder.setId(123L);
        deliveryOrder.setItems(new ArrayList<>());
        deliveryOrder.setPaymentMethod(paymentMethod);
        deliveryOrder.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setRestaurant(restaurant);
        deliveryOrder.setStatus(OrderStatus.CREATED);
        deliveryOrder.setSubtotal(BigDecimal.valueOf(42L));
        assertFalse(deliveryOrder.equals("Different type to DeliveryOrder"));
    }

    @Test
    void testEquals3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setGroups(new ArrayList<>());
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));

        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city = new City();
        city.setId(123L);
        city.setName("Name");
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
        state1.setName("Name");

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
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
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address1);
        restaurant.setCuisine(cuisine);
        restaurant.setFreightRate(BigDecimal.valueOf(42L));
        restaurant.setId(123L);
        restaurant.setName("Name");
        restaurant.setPaymentMethod(new ArrayList<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(null);
        restaurant.setUpdateDate(null);

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setAmount(BigDecimal.valueOf(42L));
        deliveryOrder.setCancellationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setCliente(user);
        deliveryOrder.setConfirmationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setDeliveryAddress(address);
        deliveryOrder.setDeliveryDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setFreightRate(BigDecimal.valueOf(42L));
        deliveryOrder.setId(123L);
        deliveryOrder.setItems(new ArrayList<>());
        deliveryOrder.setPaymentMethod(paymentMethod);
        deliveryOrder.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setRestaurant(restaurant);
        deliveryOrder.setStatus(OrderStatus.CREATED);
        deliveryOrder.setSubtotal(BigDecimal.valueOf(42L));
        assertTrue(deliveryOrder.equals(deliveryOrder));
        int expectedHashCodeResult = deliveryOrder.hashCode();
        assertEquals(expectedHashCodeResult, deliveryOrder.hashCode());
    }

    @Test
    void testEquals4() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setGroups(new ArrayList<>());
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));

        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city = new City();
        city.setId(123L);
        city.setName("Name");
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
        state1.setName("Name");

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
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
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address1);
        restaurant.setCuisine(cuisine);
        restaurant.setFreightRate(BigDecimal.valueOf(42L));
        restaurant.setId(123L);
        restaurant.setName("Name");
        restaurant.setPaymentMethod(new ArrayList<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(null);
        restaurant.setUpdateDate(null);

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setAmount(BigDecimal.valueOf(42L));
        deliveryOrder.setCancellationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setCliente(user);
        deliveryOrder.setConfirmationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setDeliveryAddress(address);
        deliveryOrder.setDeliveryDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setFreightRate(BigDecimal.valueOf(42L));
        deliveryOrder.setId(123L);
        deliveryOrder.setItems(new ArrayList<>());
        deliveryOrder.setPaymentMethod(paymentMethod);
        deliveryOrder.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setRestaurant(restaurant);
        deliveryOrder.setStatus(OrderStatus.CREATED);
        deliveryOrder.setSubtotal(BigDecimal.valueOf(42L));

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setGroups(new ArrayList<>());
        user1.setId(123L);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));

        State state2 = new State();
        state2.setId(123L);
        state2.setName("Name");

        City city2 = new City();
        city2.setId(123L);
        city2.setName("Name");
        city2.setState(state2);

        Address address2 = new Address();
        address2.setCity(city2);
        address2.setComplement("Complement");
        address2.setDistrict("District");
        address2.setNumber("42");
        address2.setStreet("Street");
        address2.setZipCode("21654");

        PaymentMethod paymentMethod1 = new PaymentMethod();
        paymentMethod1.setDescription("The characteristics of someone or something");
        paymentMethod1.setId(123L);

        State state3 = new State();
        state3.setId(123L);
        state3.setName("Name");

        City city3 = new City();
        city3.setId(123L);
        city3.setName("Name");
        city3.setState(state3);

        Address address3 = new Address();
        address3.setCity(city3);
        address3.setComplement("Complement");
        address3.setDistrict("District");
        address3.setNumber("42");
        address3.setStreet("Street");
        address3.setZipCode("21654");

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Name");
        cuisine1.setRestaurant(new ArrayList<>());

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setAddress(address3);
        restaurant1.setCuisine(cuisine1);
        restaurant1.setFreightRate(BigDecimal.valueOf(42L));
        restaurant1.setId(123L);
        restaurant1.setName("Name");
        restaurant1.setPaymentMethod(new ArrayList<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(null);
        restaurant1.setUpdateDate(null);

        DeliveryOrder deliveryOrder1 = new DeliveryOrder();
        deliveryOrder1.setAmount(BigDecimal.valueOf(42L));
        deliveryOrder1.setCancellationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder1.setCliente(user1);
        deliveryOrder1.setConfirmationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder1.setDeliveryAddress(address2);
        deliveryOrder1.setDeliveryDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder1.setFreightRate(BigDecimal.valueOf(42L));
        deliveryOrder1.setId(123L);
        deliveryOrder1.setItems(new ArrayList<>());
        deliveryOrder1.setPaymentMethod(paymentMethod1);
        deliveryOrder1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder1.setRestaurant(restaurant1);
        deliveryOrder1.setStatus(OrderStatus.CREATED);
        deliveryOrder1.setSubtotal(BigDecimal.valueOf(42L));
        assertTrue(deliveryOrder.equals(deliveryOrder1));
        int expectedHashCodeResult = deliveryOrder.hashCode();
        assertEquals(expectedHashCodeResult, deliveryOrder1.hashCode());
    }
}

