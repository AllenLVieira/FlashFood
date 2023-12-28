package br.com.allen.flashfood.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.allen.flashfood.domain.exception.ProductNotFoundException;
import br.com.allen.flashfood.domain.model.*;
import br.com.allen.flashfood.domain.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProductRegistrationService.class})
@ExtendWith(SpringExtension.class)
class ProductRegistrationServiceTest {
  State state;
  City city;
  Address address;
  Cuisine cuisine;
  Restaurant restaurant;
  Product product;
  @Autowired private ProductRegistrationService underTest;
  @MockBean private ProductRepository productRepository;

  @BeforeEach
  void setUp() {
    state = new State();
    state.setId(123L);
    state.setName("Name");

    city = new City();
    city.setId(123L);
    city.setName("Name");
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
    cuisine.setName("Name");
    cuisine.setRestaurant(new ArrayList<>());

    restaurant = new Restaurant();
    restaurant.setActive(true);
    restaurant.setAddress(address);
    restaurant.setCuisine(cuisine);
    restaurant.setFreightRate(BigDecimal.valueOf(42L));
    restaurant.setId(123L);
    restaurant.setManagers(new HashSet<>());
    restaurant.setName("Name");
    restaurant.setOpenStatus(true);
    restaurant.setPaymentMethod(new HashSet<>());
    restaurant.setProducts(new ArrayList<>());
    restaurant.setRegistrationDate(null);
    restaurant.setUpdateDate(null);

    product = new Product();
    product.setActive(true);
    product.setDescription("Description");
    product.setId(123L);
    product.setName("Name");
    product.setPrice(BigDecimal.valueOf(42L));
    product.setRestaurant(restaurant);
  }

  /** Method under test: {@link ProductRegistrationService#saveProduct(Product)} */
  @Test
  void shouldSuccessfullySaveProduct() {
    when(productRepository.save(any())).thenReturn(product);

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

    Cuisine cuisine1 = new Cuisine();
    cuisine1.setId(123L);
    cuisine1.setName("Name");
    cuisine1.setRestaurant(new ArrayList<>());

    Restaurant restaurant1 = new Restaurant();
    restaurant1.setActive(true);
    restaurant1.setAddress(address1);
    restaurant1.setCuisine(cuisine1);
    restaurant1.setFreightRate(BigDecimal.valueOf(42L));
    restaurant1.setId(123L);
    restaurant1.setManagers(new HashSet<>());
    restaurant1.setName("Name");
    restaurant1.setOpenStatus(true);
    restaurant1.setPaymentMethod(new HashSet<>());
    restaurant1.setProducts(new ArrayList<>());
    restaurant1.setRegistrationDate(null);
    restaurant1.setUpdateDate(null);

    Product product1 = new Product();
    product1.setActive(true);
    product1.setDescription("Description");
    product1.setId(123L);
    product1.setName("Name");
    product1.setPrice(BigDecimal.valueOf(42L));
    product1.setRestaurant(restaurant1);
    Product actualSaveProductResult = underTest.saveProduct(product1);
    assertSame(product, actualSaveProductResult);
    assertEquals("42", actualSaveProductResult.getPrice().toString());
    assertEquals("42", actualSaveProductResult.getRestaurant().getFreightRate().toString());
    verify(productRepository).save(any());
  }

  /** Method under test: {@link ProductRegistrationService#findProductOrElseThrow(Long, Long)} */
  @Test
  void shouldFindProductById() {
    Optional<Product> ofResult = Optional.of(product);
    when(productRepository.findById(any(), any())).thenReturn(ofResult);
    Product actualFindProductOrElseThrowResult = underTest.findProductOrElseThrow(123L, 123L);
    assertSame(product, actualFindProductOrElseThrowResult);
    assertEquals("42", actualFindProductOrElseThrowResult.getPrice().toString());
    assertEquals(
        "42", actualFindProductOrElseThrowResult.getRestaurant().getFreightRate().toString());
    verify(productRepository).findById(any(), any());
  }

  /** Method under test: {@link ProductRegistrationService#findProductOrElseThrow(Long, Long)} */
  @Test
  void shouldThrowProductNotFoundExceptionWhenEmptyProduct() {
    when(productRepository.findById(any(), any())).thenReturn(Optional.empty());
    assertThrows(
        ProductNotFoundException.class, () -> underTest.findProductOrElseThrow(123L, 123L));
    verify(productRepository).findById(any(), any());
  }

  /** Method under test: {@link ProductRegistrationService#findProductOrElseThrow(Long, Long)} */
  @Test
  void shouldThrowProductNotFoundExceptionWhenProductIdDoesNotExists() {
    when(productRepository.findById(any(), any()))
        .thenThrow(new ProductNotFoundException("An error occurred"));
    assertThrows(
        ProductNotFoundException.class, () -> underTest.findProductOrElseThrow(123L, 123L));
    verify(productRepository).findById(any(), any());
  }
}
