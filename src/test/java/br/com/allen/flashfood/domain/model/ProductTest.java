package br.com.allen.flashfood.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ProductTest {
    @Test
    void testCanEqual() {
        assertFalse((new Product()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        Product product = new Product();

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
        restaurant.setRegistrationDate(LocalDateTime.of(3, 3, 3, 3, 1));
        restaurant.setUpdateDate(LocalDateTime.of(3, 3, 3, 3, 1));

        Product product1 = new Product();
        product1.setActive(true);
        product1.setDescription("The characteristics of someone or something");
        product1.setId(123L);
        product1.setName("Name");
        product1.setPrice(BigDecimal.valueOf(42L));
        product1.setRestaurant(restaurant);
        assertTrue(product.canEqual(product1));
    }

    @Test
    void testConstructor() {
        Product actualProduct = new Product();
        actualProduct.setActive(true);
        actualProduct.setDescription("The characteristics of someone or something");
        actualProduct.setId(123L);
        actualProduct.setName("Name");
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        actualProduct.setPrice(valueOfResult);
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
        BigDecimal valueOfResult1 = BigDecimal.valueOf(42L);
        restaurant.setFreightRate(valueOfResult1);
        restaurant.setId(123L);
        restaurant.setName("Name");
        restaurant.setPaymentMethod(new ArrayList<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));
        actualProduct.setRestaurant(restaurant);
        assertEquals("The characteristics of someone or something", actualProduct.getDescription());
        assertEquals(123L, actualProduct.getId());
        assertEquals("Name", actualProduct.getName());
        BigDecimal price = actualProduct.getPrice();
        assertSame(valueOfResult, price);
        assertEquals(valueOfResult1, price);
        assertSame(restaurant, actualProduct.getRestaurant());
        assertTrue(actualProduct.isActive());
        assertEquals(
                "Product(id=123, name=Name, description=The characteristics of someone or something, price=42, active=true,"
                        + " restaurant=Restaurant(id=123, name=Name, freightRate=42, address=Address(zipCode=21654, street=Street,"
                        + " number=42, city=City(id=123, name=Name, state=State(id=123, name=Name)), district=District,"
                        + " complement=Complement), registrationDate=0001-01-01T01:01, updateDate=0001-01-01T01:01, cuisine=Cuisine"
                        + "(id=123, name=Name, restaurant=[]), products=[], paymentMethod=[]))",
                actualProduct.toString());
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

        Product product = new Product();
        product.setActive(true);
        product.setDescription("The characteristics of someone or something");
        product.setId(123L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setRestaurant(restaurant);
        assertFalse(product.equals(null));
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

        Product product = new Product();
        product.setActive(true);
        product.setDescription("The characteristics of someone or something");
        product.setId(123L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setRestaurant(restaurant);
        assertFalse(product.equals("Different type to Product"));
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

        Product product = new Product();
        product.setActive(true);
        product.setDescription("The characteristics of someone or something");
        product.setId(123L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setRestaurant(restaurant);
        assertTrue(product.equals(product));
        int expectedHashCodeResult = product.hashCode();
        assertEquals(expectedHashCodeResult, product.hashCode());
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

        Product product = new Product();
        product.setActive(true);
        product.setDescription("The characteristics of someone or something");
        product.setId(123L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setRestaurant(restaurant);

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

        Product product1 = new Product();
        product1.setActive(true);
        product1.setDescription("The characteristics of someone or something");
        product1.setId(123L);
        product1.setName("Name");
        product1.setPrice(BigDecimal.valueOf(42L));
        product1.setRestaurant(restaurant1);
        assertTrue(product.equals(product1));
        int expectedHashCodeResult = product.hashCode();
        assertEquals(expectedHashCodeResult, product1.hashCode());
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
        restaurant.setId(123L);
        restaurant.setName("Name");
        restaurant.setPaymentMethod(new ArrayList<>());
        restaurant.setProducts(new ArrayList<>());
        restaurant.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        restaurant.setUpdateDate(LocalDateTime.of(1, 1, 1, 1, 1));

        Product product = new Product();
        product.setActive(true);
        product.setDescription("The characteristics of someone or something");
        product.setId(1L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setRestaurant(restaurant);

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

        Product product1 = new Product();
        product1.setActive(true);
        product1.setDescription("The characteristics of someone or something");
        product1.setId(123L);
        product1.setName("Name");
        product1.setPrice(BigDecimal.valueOf(42L));
        product1.setRestaurant(restaurant1);
        assertFalse(product.equals(product1));
    }
}

