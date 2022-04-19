package br.com.allen.flashfood.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class OrderItemTest {
    @Test
    void testCanEqual() {
        assertFalse((new OrderItem()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        OrderItem orderItem = new OrderItem();

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

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
        city1.setState(new State());

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
        restaurant.setRegistrationDate(LocalDateTime.of(3, 3, 3, 3, 1));
        restaurant.setUpdateDate(LocalDateTime.of(3, 3, 3, 3, 1));

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setAmount(BigDecimal.valueOf(42L));
        deliveryOrder.setCancellationDate(LocalDateTime.of(3, 3, 3, 3, 1));
        deliveryOrder.setCliente(user);
        deliveryOrder.setConfirmationDate(LocalDateTime.of(3, 3, 3, 3, 1));
        deliveryOrder.setDeliveryAddress(address);
        deliveryOrder.setDeliveryDate(LocalDateTime.of(3, 3, 3, 3, 1));
        deliveryOrder.setFreightRate(BigDecimal.valueOf(42L));
        deliveryOrder.setId(123L);
        deliveryOrder.setItems(new ArrayList<>());
        deliveryOrder.setPaymentMethod(paymentMethod);
        deliveryOrder.setRegistrationDate(LocalDateTime.of(3, 3, 3, 3, 1));
        deliveryOrder.setRestaurant(restaurant);
        deliveryOrder.setStatus(OrderStatus.CREATED);
        deliveryOrder.setSubtotal(BigDecimal.valueOf(42L));

        City city2 = new City();
        city2.setId(123L);
        city2.setName("Name");
        city2.setState(new State());

        Address address2 = new Address();
        address2.setCity(city2);
        address2.setComplement("Complement");
        address2.setDistrict("District");
        address2.setNumber("42");
        address2.setStreet("Street");
        address2.setZipCode("21654");

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Name");
        cuisine1.setRestaurant(new ArrayList<>());

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setAddress(address2);
        restaurant1.setCuisine(cuisine1);
        restaurant1.setFreightRate(BigDecimal.valueOf(42L));
        restaurant1.setId(123L);
        restaurant1.setName("Name");
        restaurant1.setPaymentMethod(new ArrayList<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(LocalDateTime.of(3, 3, 3, 3, 1));
        restaurant1.setUpdateDate(LocalDateTime.of(3, 3, 3, 3, 1));

        Product product = new Product();
        product.setActive(true);
        product.setDescription("The characteristics of someone or something");
        product.setId(123L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setRestaurant(restaurant1);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(123L);
        orderItem1.setNote("Note");
        orderItem1.setOrder(deliveryOrder);
        orderItem1.setProduct(product);
        orderItem1.setQuantity(3);
        orderItem1.setTotalPrice(BigDecimal.valueOf(42L));
        orderItem1.setUnitPrice(BigDecimal.valueOf(42L));
        assertTrue(orderItem.canEqual(orderItem1));
    }

    @Test
    void testConstructor() {
        OrderItem actualOrderItem = new OrderItem();
        actualOrderItem.setId(123L);
        actualOrderItem.setNote("Note");
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
        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
        city1.setState(new State());
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
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        restaurant.setFreightRate(valueOfResult);
        restaurant.setId(123L);
        restaurant.setName("Name");
        restaurant.setPaymentMethod(new ArrayList<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        BigDecimal valueOfResult1 = BigDecimal.valueOf(42L);
        deliveryOrder.setAmount(valueOfResult1);
        deliveryOrder.setCancellationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setCliente(user);
        deliveryOrder.setConfirmationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setDeliveryAddress(address);
        deliveryOrder.setDeliveryDate(LocalDateTime.of(1, 1, 1, 1, 1));
        BigDecimal valueOfResult2 = BigDecimal.valueOf(42L);
        deliveryOrder.setFreightRate(valueOfResult2);
        deliveryOrder.setId(123L);
        deliveryOrder.setItems(new ArrayList<>());
        deliveryOrder.setPaymentMethod(paymentMethod);
        deliveryOrder.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder.setRestaurant(restaurant);
        deliveryOrder.setStatus(OrderStatus.CREATED);
        BigDecimal valueOfResult3 = BigDecimal.valueOf(42L);
        deliveryOrder.setSubtotal(valueOfResult3);
        actualOrderItem.setOrder(deliveryOrder);
        City city2 = new City();
        city2.setId(123L);
        city2.setName("Name");
        city2.setState(new State());
        Address address2 = new Address();
        address2.setCity(city2);
        address2.setComplement("Complement");
        address2.setDistrict("District");
        address2.setNumber("42");
        address2.setStreet("Street");
        address2.setZipCode("21654");
        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Name");
        cuisine1.setRestaurant(new ArrayList<>());
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setAddress(address2);
        restaurant1.setCuisine(cuisine1);
        BigDecimal valueOfResult4 = BigDecimal.valueOf(42L);
        restaurant1.setFreightRate(valueOfResult4);
        restaurant1.setId(123L);
        restaurant1.setName("Name");
        restaurant1.setPaymentMethod(new ArrayList<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant1.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        Product product = new Product();
        product.setActive(true);
        product.setDescription("The characteristics of someone or something");
        product.setId(123L);
        product.setName("Name");
        BigDecimal valueOfResult5 = BigDecimal.valueOf(42L);
        product.setPrice(valueOfResult5);
        product.setRestaurant(restaurant1);
        actualOrderItem.setProduct(product);
        actualOrderItem.setQuantity(1);
        BigDecimal valueOfResult6 = BigDecimal.valueOf(42L);
        actualOrderItem.setTotalPrice(valueOfResult6);
        BigDecimal valueOfResult7 = BigDecimal.valueOf(42L);
        actualOrderItem.setUnitPrice(valueOfResult7);
        assertEquals(123L, actualOrderItem.getId().longValue());
        assertEquals("Note", actualOrderItem.getNote());
        assertSame(deliveryOrder, actualOrderItem.getOrder());
        assertSame(product, actualOrderItem.getProduct());
        assertEquals(1, actualOrderItem.getQuantity().intValue());
        BigDecimal totalPrice = actualOrderItem.getTotalPrice();
        assertSame(valueOfResult6, totalPrice);
        assertEquals(valueOfResult1, totalPrice);
        assertEquals(valueOfResult2, totalPrice);
        assertEquals(valueOfResult, totalPrice);
        assertEquals(valueOfResult3, totalPrice);
        assertEquals(valueOfResult5, totalPrice);
        assertEquals(valueOfResult4, totalPrice);
        BigDecimal unitPrice = actualOrderItem.getUnitPrice();
        assertEquals(unitPrice, totalPrice);
        assertSame(valueOfResult7, unitPrice);
        assertEquals(valueOfResult1, unitPrice);
        assertEquals(valueOfResult2, unitPrice);
        assertEquals(valueOfResult, unitPrice);
        assertEquals(valueOfResult3, unitPrice);
        assertEquals(valueOfResult5, unitPrice);
        assertEquals(valueOfResult4, unitPrice);
        assertEquals(valueOfResult6, unitPrice);
        assertEquals("OrderItem(id=123, unitPrice=42, totalPrice=42, quantity=1, note=Note, order=DeliveryOrder(id=123,"
                + " subtotal=42, freightRate=42, amount=42, deliveryAddress=Address(zipCode=21654, street=Street, number=42,"
                + " city=City(id=123, name=Name, state=State(id=123, name=Name)), district=District, complement=Complement),"
                + " status=CREATED, registrationDate=0001-01-01T01:01, confirmationDate=0001-01-01T01:01, cancellationDate"
                + "=0001-01-01T01:01, deliveryDate=0001-01-01T01:01, paymentMethod=PaymentMethod(id=123, description=The"
                + " characteristics of someone or something), restaurant=Restaurant(id=123, name=Name, freightRate=42,"
                + " address=Address(zipCode=21654, street=Street, number=42, city=City(id=123, name=Name, state=State(id=null,"
                + " name=null)), district=District, complement=Complement), registrationDate=0001-01-01T01:01,"
                + " updateDate=0001-01-01T01:01, cuisine=Cuisine(id=123, name=Name, restaurant=[]), products=[],"
                + " paymentMethod=[]), cliente=User(id=123, name=Name, email=jane.doe@example.org, password=iloveyou,"
                + " registrationDate=0001-01-01T01:01, groups=[]), items=[]), product=Product(id=123, name=Name,"
                + " description=The characteristics of someone or something, price=42, active=true, restaurant=Restaurant"
                + "(id=123, name=Name, freightRate=42, address=Address(zipCode=21654, street=Street, number=42,"
                + " city=City(id=123, name=Name, state=State(id=null, name=null)), district=District, complement=Complement),"
                + " registrationDate=0001-01-01T01:01, updateDate=0001-01-01T01:01, cuisine=Cuisine(id=123, name=Name,"
                + " restaurant=[]), products=[], paymentMethod=[])))", actualOrderItem.toString());
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

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
        city1.setState(new State());

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
        restaurant.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));

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

        City city2 = new City();
        city2.setId(123L);
        city2.setName("Name");
        city2.setState(new State());

        Address address2 = new Address();
        address2.setCity(city2);
        address2.setComplement("Complement");
        address2.setDistrict("District");
        address2.setNumber("42");
        address2.setStreet("Street");
        address2.setZipCode("21654");

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Name");
        cuisine1.setRestaurant(new ArrayList<>());

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setAddress(address2);
        restaurant1.setCuisine(cuisine1);
        restaurant1.setFreightRate(BigDecimal.valueOf(42L));
        restaurant1.setId(123L);
        restaurant1.setName("Name");
        restaurant1.setPaymentMethod(new ArrayList<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant1.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));

        Product product = new Product();
        product.setActive(true);
        product.setDescription("The characteristics of someone or something");
        product.setId(123L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setRestaurant(restaurant1);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(123L);
        orderItem.setNote("Note");
        orderItem.setOrder(deliveryOrder);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setTotalPrice(BigDecimal.valueOf(42L));
        orderItem.setUnitPrice(BigDecimal.valueOf(42L));
        assertFalse(orderItem.equals(null));
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

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
        city1.setState(new State());

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
        restaurant.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));

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

        City city2 = new City();
        city2.setId(123L);
        city2.setName("Name");
        city2.setState(new State());

        Address address2 = new Address();
        address2.setCity(city2);
        address2.setComplement("Complement");
        address2.setDistrict("District");
        address2.setNumber("42");
        address2.setStreet("Street");
        address2.setZipCode("21654");

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Name");
        cuisine1.setRestaurant(new ArrayList<>());

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setAddress(address2);
        restaurant1.setCuisine(cuisine1);
        restaurant1.setFreightRate(BigDecimal.valueOf(42L));
        restaurant1.setId(123L);
        restaurant1.setName("Name");
        restaurant1.setPaymentMethod(new ArrayList<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant1.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));

        Product product = new Product();
        product.setActive(true);
        product.setDescription("The characteristics of someone or something");
        product.setId(123L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setRestaurant(restaurant1);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(123L);
        orderItem.setNote("Note");
        orderItem.setOrder(deliveryOrder);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setTotalPrice(BigDecimal.valueOf(42L));
        orderItem.setUnitPrice(BigDecimal.valueOf(42L));
        assertFalse(orderItem.equals("Different type to OrderItem"));
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

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
        city1.setState(new State());

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
        restaurant.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));

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

        City city2 = new City();
        city2.setId(123L);
        city2.setName("Name");
        city2.setState(new State());

        Address address2 = new Address();
        address2.setCity(city2);
        address2.setComplement("Complement");
        address2.setDistrict("District");
        address2.setNumber("42");
        address2.setStreet("Street");
        address2.setZipCode("21654");

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Name");
        cuisine1.setRestaurant(new ArrayList<>());

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setAddress(address2);
        restaurant1.setCuisine(cuisine1);
        restaurant1.setFreightRate(BigDecimal.valueOf(42L));
        restaurant1.setId(123L);
        restaurant1.setName("Name");
        restaurant1.setPaymentMethod(new ArrayList<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant1.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));

        Product product = new Product();
        product.setActive(true);
        product.setDescription("The characteristics of someone or something");
        product.setId(123L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setRestaurant(restaurant1);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(123L);
        orderItem.setNote("Note");
        orderItem.setOrder(deliveryOrder);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setTotalPrice(BigDecimal.valueOf(42L));
        orderItem.setUnitPrice(BigDecimal.valueOf(42L));
        assertTrue(orderItem.equals(orderItem));
        int expectedHashCodeResult = orderItem.hashCode();
        assertEquals(expectedHashCodeResult, orderItem.hashCode());
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

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
        city1.setState(new State());

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
        restaurant.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));

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

        City city2 = new City();
        city2.setId(123L);
        city2.setName("Name");
        city2.setState(new State());

        Address address2 = new Address();
        address2.setCity(city2);
        address2.setComplement("Complement");
        address2.setDistrict("District");
        address2.setNumber("42");
        address2.setStreet("Street");
        address2.setZipCode("21654");

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Name");
        cuisine1.setRestaurant(new ArrayList<>());

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setAddress(address2);
        restaurant1.setCuisine(cuisine1);
        restaurant1.setFreightRate(BigDecimal.valueOf(42L));
        restaurant1.setId(123L);
        restaurant1.setName("Name");
        restaurant1.setPaymentMethod(new ArrayList<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant1.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));

        Product product = new Product();
        product.setActive(true);
        product.setDescription("The characteristics of someone or something");
        product.setId(123L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setRestaurant(restaurant1);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(123L);
        orderItem.setNote("Note");
        orderItem.setOrder(deliveryOrder);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setTotalPrice(BigDecimal.valueOf(42L));
        orderItem.setUnitPrice(BigDecimal.valueOf(42L));

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setGroups(new ArrayList<>());
        user1.setId(123L);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));

        State state1 = new State();
        state1.setId(123L);
        state1.setName("Name");

        City city3 = new City();
        city3.setId(123L);
        city3.setName("Name");
        city3.setState(state1);

        Address address3 = new Address();
        address3.setCity(city3);
        address3.setComplement("Complement");
        address3.setDistrict("District");
        address3.setNumber("42");
        address3.setStreet("Street");
        address3.setZipCode("21654");

        PaymentMethod paymentMethod1 = new PaymentMethod();
        paymentMethod1.setDescription("The characteristics of someone or something");
        paymentMethod1.setId(123L);

        City city4 = new City();
        city4.setId(123L);
        city4.setName("Name");
        city4.setState(new State());

        Address address4 = new Address();
        address4.setCity(city4);
        address4.setComplement("Complement");
        address4.setDistrict("District");
        address4.setNumber("42");
        address4.setStreet("Street");
        address4.setZipCode("21654");

        Cuisine cuisine2 = new Cuisine();
        cuisine2.setId(123L);
        cuisine2.setName("Name");
        cuisine2.setRestaurant(new ArrayList<>());

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setAddress(address4);
        restaurant2.setCuisine(cuisine2);
        restaurant2.setFreightRate(BigDecimal.valueOf(42L));
        restaurant2.setId(123L);
        restaurant2.setName("Name");
        restaurant2.setPaymentMethod(new ArrayList<>());
        restaurant2.setProducts(new ArrayList<>());
        restaurant2.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant2.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));

        DeliveryOrder deliveryOrder1 = new DeliveryOrder();
        deliveryOrder1.setAmount(BigDecimal.valueOf(42L));
        deliveryOrder1.setCancellationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder1.setCliente(user1);
        deliveryOrder1.setConfirmationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder1.setDeliveryAddress(address3);
        deliveryOrder1.setDeliveryDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder1.setFreightRate(BigDecimal.valueOf(42L));
        deliveryOrder1.setId(123L);
        deliveryOrder1.setItems(new ArrayList<>());
        deliveryOrder1.setPaymentMethod(paymentMethod1);
        deliveryOrder1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        deliveryOrder1.setRestaurant(restaurant2);
        deliveryOrder1.setStatus(OrderStatus.CREATED);
        deliveryOrder1.setSubtotal(BigDecimal.valueOf(42L));

        City city5 = new City();
        city5.setId(123L);
        city5.setName("Name");
        city5.setState(new State());

        Address address5 = new Address();
        address5.setCity(city5);
        address5.setComplement("Complement");
        address5.setDistrict("District");
        address5.setNumber("42");
        address5.setStreet("Street");
        address5.setZipCode("21654");

        Cuisine cuisine3 = new Cuisine();
        cuisine3.setId(123L);
        cuisine3.setName("Name");
        cuisine3.setRestaurant(new ArrayList<>());

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setAddress(address5);
        restaurant3.setCuisine(cuisine3);
        restaurant3.setFreightRate(BigDecimal.valueOf(42L));
        restaurant3.setId(123L);
        restaurant3.setName("Name");
        restaurant3.setPaymentMethod(new ArrayList<>());
        restaurant3.setProducts(new ArrayList<>());
        restaurant3.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant3.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));

        Product product1 = new Product();
        product1.setActive(true);
        product1.setDescription("The characteristics of someone or something");
        product1.setId(123L);
        product1.setName("Name");
        product1.setPrice(BigDecimal.valueOf(42L));
        product1.setRestaurant(restaurant3);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(123L);
        orderItem1.setNote("Note");
        orderItem1.setOrder(deliveryOrder1);
        orderItem1.setProduct(product1);
        orderItem1.setQuantity(1);
        orderItem1.setTotalPrice(BigDecimal.valueOf(42L));
        orderItem1.setUnitPrice(BigDecimal.valueOf(42L));
        assertTrue(orderItem.equals(orderItem1));
        int expectedHashCodeResult = orderItem.hashCode();
        assertEquals(expectedHashCodeResult, orderItem1.hashCode());
    }
}

