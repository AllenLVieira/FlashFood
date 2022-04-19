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

class RestaurantTest {
    @Test
    void testCanEqual() {
        assertFalse((new Restaurant()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        Restaurant restaurant = new Restaurant();

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

        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setAddress(address);
        restaurant1.setCuisine(cuisine);
        restaurant1.setFreightRate(BigDecimal.valueOf(42L));
        restaurant1.setId(123L);
        restaurant1.setName("Name");
        restaurant1.setPaymentMethod(new ArrayList<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(LocalDateTime.of(3, 3, 3, 3, 1));
        restaurant1.setUpdateDate(LocalDateTime.of(3, 3, 3, 3, 1));
        assertTrue(restaurant.canEqual(restaurant1));
    }

    @Test
    void testConstructor() {
        Restaurant actualRestaurant = new Restaurant();
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
        actualRestaurant.setAddress(address);
        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Name");
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        cuisine.setRestaurant(restaurantList);
        actualRestaurant.setCuisine(cuisine);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        actualRestaurant.setFreightRate(valueOfResult);
        actualRestaurant.setId(123L);
        actualRestaurant.setName("Name");
        ArrayList<PaymentMethod> paymentMethodList = new ArrayList<>();
        actualRestaurant.setPaymentMethod(paymentMethodList);
        ArrayList<Product> productList = new ArrayList<>();
        actualRestaurant.setProducts(productList);
        LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
        actualRestaurant.setRegistrationDate(ofResult);
        LocalDateTime ofResult1 = LocalDateTime.of(1, 1, 1, 1, 1);
        actualRestaurant.setUpdateDate(ofResult1);
        assertSame(address, actualRestaurant.getAddress());
        assertSame(cuisine, actualRestaurant.getCuisine());
        assertSame(valueOfResult, actualRestaurant.getFreightRate());
        assertEquals(123L, actualRestaurant.getId().longValue());
        assertEquals("Name", actualRestaurant.getName());
        List<PaymentMethod> paymentMethod = actualRestaurant.getPaymentMethod();
        assertSame(paymentMethodList, paymentMethod);
        assertEquals(restaurantList, paymentMethod);
        assertEquals(productList, paymentMethod);
        List<Product> products = actualRestaurant.getProducts();
        assertSame(productList, products);
        assertEquals(restaurantList, products);
        assertEquals(paymentMethod, products);
        assertSame(ofResult, actualRestaurant.getRegistrationDate());
        assertSame(ofResult1, actualRestaurant.getUpdateDate());
        assertEquals(
                "Restaurant(id=123, name=Name, freightRate=42, address=Address(zipCode=21654, street=Street, number=42,"
                        + " city=City(id=123, name=Name, state=State(id=123, name=Name)), district=District, complement=Complement),"
                        + " registrationDate=0001-01-01T01:01, updateDate=0001-01-01T01:01, cuisine=Cuisine(id=123, name=Name,"
                        + " restaurant=[]), products=[], paymentMethod=[])",
                actualRestaurant.toString());
    }

    @Test
    void testEquals() {
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

        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setCuisine(cuisine);
        restaurant.setFreightRate(BigDecimal.valueOf(42L));
        restaurant.setId(123L);
        restaurant.setName("Name");
        restaurant.setPaymentMethod(new ArrayList<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        assertFalse(restaurant.equals(null));
    }

    @Test
    void testEquals2() {
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

        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setCuisine(cuisine);
        restaurant.setFreightRate(BigDecimal.valueOf(42L));
        restaurant.setId(123L);
        restaurant.setName("Name");
        restaurant.setPaymentMethod(new ArrayList<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        assertFalse(restaurant.equals("Different type to Restaurant"));
    }

    @Test
    void testEquals3() {
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

        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setCuisine(cuisine);
        restaurant.setFreightRate(BigDecimal.valueOf(42L));
        restaurant.setId(123L);
        restaurant.setName("Name");
        restaurant.setPaymentMethod(new ArrayList<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        assertTrue(restaurant.equals(restaurant));
        int expectedHashCodeResult = restaurant.hashCode();
        assertEquals(expectedHashCodeResult, restaurant.hashCode());
    }

    @Test
    void testEquals4() {
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

        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setCuisine(cuisine);
        restaurant.setFreightRate(BigDecimal.valueOf(42L));
        restaurant.setId(123L);
        restaurant.setName("Name");
        restaurant.setPaymentMethod(new ArrayList<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));

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

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Name");
        cuisine1.setRestaurant(new ArrayList<>());

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setAddress(address1);
        restaurant1.setCuisine(cuisine1);
        restaurant1.setFreightRate(BigDecimal.valueOf(42L));
        restaurant1.setId(123L);
        restaurant1.setName("Name");
        restaurant1.setPaymentMethod(new ArrayList<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant1.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        assertTrue(restaurant.equals(restaurant1));
        int expectedHashCodeResult = restaurant.hashCode();
        assertEquals(expectedHashCodeResult, restaurant1.hashCode());
    }

    @Test
    void testEquals5() {
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

        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setCuisine(cuisine);
        restaurant.setFreightRate(BigDecimal.valueOf(42L));
        restaurant.setId(1L);
        restaurant.setName("Name");
        restaurant.setPaymentMethod(new ArrayList<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));

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

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Name");
        cuisine1.setRestaurant(new ArrayList<>());

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setAddress(address1);
        restaurant1.setCuisine(cuisine1);
        restaurant1.setFreightRate(BigDecimal.valueOf(42L));
        restaurant1.setId(123L);
        restaurant1.setName("Name");
        restaurant1.setPaymentMethod(new ArrayList<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant1.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        assertFalse(restaurant.equals(restaurant1));
    }

    @Test
    void testEquals6() {
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

        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setCuisine(cuisine);
        restaurant.setFreightRate(BigDecimal.valueOf(42L));
        restaurant.setId(null);
        restaurant.setName("Name");
        restaurant.setPaymentMethod(new ArrayList<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));

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

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Name");
        cuisine1.setRestaurant(new ArrayList<>());

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setAddress(address1);
        restaurant1.setCuisine(cuisine1);
        restaurant1.setFreightRate(BigDecimal.valueOf(42L));
        restaurant1.setId(123L);
        restaurant1.setName("Name");
        restaurant1.setPaymentMethod(new ArrayList<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant1.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        assertFalse(restaurant.equals(restaurant1));
    }
}

