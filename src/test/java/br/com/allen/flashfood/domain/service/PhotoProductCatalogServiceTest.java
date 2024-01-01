package br.com.allen.flashfood.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.allen.flashfood.domain.exception.PhotoProductNotFoundException;
import br.com.allen.flashfood.domain.model.Address;
import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.model.PhotoProduct;
import br.com.allen.flashfood.domain.model.Product;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.ProductRepository;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PhotoProductCatalogService.class})
@ExtendWith(SpringExtension.class)
class PhotoProductCatalogServiceTest {
  @Autowired private PhotoProductCatalogService photoProductCatalogService;

  @MockBean private PhotoStorageService photoStorageService;

  @MockBean private ProductRepository productRepository;

  /** Method under test: {@link PhotoProductCatalogService#save(PhotoProduct, InputStream)} */
  @Test
  void testSave() throws UnsupportedEncodingException {
    // Arrange
    City city = new City();
    city.setId(1L);
    city.setName("Name");
    city.setState(new State());

    Address address = new Address();
    address.setCity(city);
    address.setComplement("Complement");
    address.setDistrict("District");
    address.setNumber("42");
    address.setStreet("Street");
    address.setZipCode("21654");

    Cuisine cuisine = new Cuisine();
    cuisine.setId(1L);
    cuisine.setName("Name");
    cuisine.setRestaurant(new ArrayList<>());

    Restaurant restaurant = new Restaurant();
    restaurant.setActive(true);
    restaurant.setAddress(address);
    restaurant.setCuisine(cuisine);
    restaurant.setFreightRate(new BigDecimal("2.3"));
    restaurant.setId(1L);
    restaurant.setManagers(new HashSet<>());
    restaurant.setName("Name");
    restaurant.setOpenStatus(true);
    restaurant.setPaymentMethod(new HashSet<>());
    restaurant.setProducts(new ArrayList<>());
    restaurant.setRegistrationDate(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
    restaurant.setUpdateDate(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));

    Product product = new Product();
    product.setActive(true);
    product.setDescription("The characteristics of someone or something");
    product.setId(1L);
    product.setName("Name");
    product.setPrice(new BigDecimal("2.3"));
    product.setRestaurant(restaurant);

    PhotoProduct photoProduct = new PhotoProduct();
    photoProduct.setContentType("text/plain");
    photoProduct.setDescription("The characteristics of someone or something");
    photoProduct.setFilename("foo.txt");
    photoProduct.setFilesize(3L);
    photoProduct.setId(1L);
    photoProduct.setProduct(product);

    Address address2 = new Address();
    address2.setCity(new City());
    address2.setComplement("Complement");
    address2.setDistrict("District");
    address2.setNumber("42");
    address2.setStreet("Street");
    address2.setZipCode("21654");

    Cuisine cuisine2 = new Cuisine();
    cuisine2.setId(1L);
    cuisine2.setName("Name");
    cuisine2.setRestaurant(new ArrayList<>());

    Restaurant restaurant2 = new Restaurant();
    restaurant2.setActive(true);
    restaurant2.setAddress(address2);
    restaurant2.setCuisine(cuisine2);
    restaurant2.setFreightRate(new BigDecimal("2.3"));
    restaurant2.setId(1L);
    restaurant2.setManagers(new HashSet<>());
    restaurant2.setName("Name");
    restaurant2.setOpenStatus(true);
    restaurant2.setPaymentMethod(new HashSet<>());
    restaurant2.setProducts(new ArrayList<>());
    restaurant2.setRegistrationDate(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
    restaurant2.setUpdateDate(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));

    Product product2 = new Product();
    product2.setActive(true);
    product2.setDescription("The characteristics of someone or something");
    product2.setId(1L);
    product2.setName("Name");
    product2.setPrice(new BigDecimal("2.3"));
    product2.setRestaurant(restaurant2);

    PhotoProduct photoProduct2 = new PhotoProduct();
    photoProduct2.setContentType("text/plain");
    photoProduct2.setDescription("The characteristics of someone or something");
    photoProduct2.setFilename("foo.txt");
    photoProduct2.setFilesize(3L);
    photoProduct2.setId(1L);
    photoProduct2.setProduct(product2);
    Optional<PhotoProduct> ofResult = Optional.of(photoProduct2);
    doNothing().when(productRepository).delete(Mockito.<PhotoProduct>any());
    when(productRepository.save(Mockito.<PhotoProduct>any())).thenReturn(photoProduct);
    when(productRepository.findPhotoById(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);
    doNothing().when(productRepository).flush();
    when(photoStorageService.generateFileName(Mockito.any())).thenReturn("foo.txt");
    doNothing()
        .when(photoStorageService)
        .replaceName(Mockito.any(), Mockito.any());

    Address address3 = new Address();
    address3.setCity(new City());
    address3.setComplement("Complement");
    address3.setDistrict("District");
    address3.setNumber("42");
    address3.setStreet("Street");
    address3.setZipCode("21654");

    Cuisine cuisine3 = new Cuisine();
    cuisine3.setId(1L);
    cuisine3.setName("Name");
    cuisine3.setRestaurant(new ArrayList<>());

    Restaurant restaurant3 = new Restaurant();
    restaurant3.setActive(true);
    restaurant3.setAddress(address3);
    restaurant3.setCuisine(cuisine3);
    restaurant3.setFreightRate(new BigDecimal("2.3"));
    restaurant3.setId(1L);
    restaurant3.setManagers(new HashSet<>());
    restaurant3.setName("Name");
    restaurant3.setOpenStatus(true);
    restaurant3.setPaymentMethod(new HashSet<>());
    restaurant3.setProducts(new ArrayList<>());
    restaurant3.setRegistrationDate(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
    restaurant3.setUpdateDate(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));

    Product product3 = new Product();
    product3.setActive(true);
    product3.setDescription("The characteristics of someone or something");
    product3.setId(1L);
    product3.setName("Name");
    product3.setPrice(new BigDecimal("2.3"));
    product3.setRestaurant(restaurant3);

    PhotoProduct photo = new PhotoProduct();
    photo.setContentType("text/plain");
    photo.setDescription("The characteristics of someone or something");
    photo.setFilename("foo.txt");
    photo.setFilesize(3L);
    photo.setId(1L);
    photo.setProduct(product3);

    // Act
    PhotoProduct actualSaveResult =
        photoProductCatalogService.save(
            photo, new ByteArrayInputStream("AXAXAXAX".getBytes(StandardCharsets.UTF_8)));

    // Assert
    verify(productRepository).findPhotoById(Mockito.<Long>any(), Mockito.<Long>any());
    verify(productRepository).delete(Mockito.<PhotoProduct>any());
    verify(productRepository).save(Mockito.<PhotoProduct>any());
    verify(photoStorageService).generateFileName(Mockito.any());
    verify(photoStorageService)
        .replaceName(Mockito.any(), Mockito.any());
    verify(productRepository).flush();
    assertEquals("foo.txt", photo.getFilename());
    assertSame(photoProduct, actualSaveResult);
  }

  /** Method under test: {@link PhotoProductCatalogService#save(PhotoProduct, InputStream)} */
  @Test
  void testSave2() throws UnsupportedEncodingException {
    // Arrange
    when(photoStorageService.generateFileName(Mockito.any()))
        .thenThrow(new PhotoProductNotFoundException("An error occurred"));

    Address address = new Address();
    address.setCity(new City());
    address.setComplement("Complement");
    address.setDistrict("District");
    address.setNumber("42");
    address.setStreet("Street");
    address.setZipCode("21654");

    Cuisine cuisine = new Cuisine();
    cuisine.setId(1L);
    cuisine.setName("Name");
    cuisine.setRestaurant(new ArrayList<>());

    Restaurant restaurant = new Restaurant();
    restaurant.setActive(true);
    restaurant.setAddress(address);
    restaurant.setCuisine(cuisine);
    restaurant.setFreightRate(new BigDecimal("2.3"));
    restaurant.setId(1L);
    restaurant.setManagers(new HashSet<>());
    restaurant.setName("Name");
    restaurant.setOpenStatus(true);
    restaurant.setPaymentMethod(new HashSet<>());
    restaurant.setProducts(new ArrayList<>());
    restaurant.setRegistrationDate(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
    restaurant.setUpdateDate(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));

    Product product = new Product();
    product.setActive(true);
    product.setDescription("The characteristics of someone or something");
    product.setId(1L);
    product.setName("Name");
    product.setPrice(new BigDecimal("2.3"));
    product.setRestaurant(restaurant);

    PhotoProduct photo = new PhotoProduct();
    photo.setContentType("text/plain");
    photo.setDescription("The characteristics of someone or something");
    photo.setFilename("foo.txt");
    photo.setFilesize(3L);
    photo.setId(1L);
    photo.setProduct(product);

    // Act and Assert
    assertThrows(
        PhotoProductNotFoundException.class,
        () ->
            photoProductCatalogService.save(
                photo, new ByteArrayInputStream("AXAXAXAX".getBytes(StandardCharsets.UTF_8))));
    verify(photoStorageService).generateFileName(Mockito.any());
  }

  /** Method under test: {@link PhotoProductCatalogService#remove(Long, Long)} */
  @Test
  void testRemove() {
    // Arrange
    Address address = new Address();
    address.setCity(new City());
    address.setComplement("Complement");
    address.setDistrict("District");
    address.setNumber("42");
    address.setStreet("Street");
    address.setZipCode("21654");

    Cuisine cuisine = new Cuisine();
    cuisine.setId(1L);
    cuisine.setName("Name");
    cuisine.setRestaurant(new ArrayList<>());

    Restaurant restaurant = new Restaurant();
    restaurant.setActive(true);
    restaurant.setAddress(address);
    restaurant.setCuisine(cuisine);
    restaurant.setFreightRate(new BigDecimal("2.3"));
    restaurant.setId(1L);
    restaurant.setManagers(new HashSet<>());
    restaurant.setName("Name");
    restaurant.setOpenStatus(true);
    restaurant.setPaymentMethod(new HashSet<>());
    restaurant.setProducts(new ArrayList<>());
    restaurant.setRegistrationDate(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
    restaurant.setUpdateDate(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));

    Product product = new Product();
    product.setActive(true);
    product.setDescription("The characteristics of someone or something");
    product.setId(1L);
    product.setName("Name");
    product.setPrice(new BigDecimal("2.3"));
    product.setRestaurant(restaurant);

    PhotoProduct photoProduct = new PhotoProduct();
    photoProduct.setContentType("text/plain");
    photoProduct.setDescription("The characteristics of someone or something");
    photoProduct.setFilename("foo.txt");
    photoProduct.setFilesize(3L);
    photoProduct.setId(1L);
    photoProduct.setProduct(product);
    Optional<PhotoProduct> ofResult = Optional.of(photoProduct);
    doNothing().when(productRepository).delete(Mockito.<PhotoProduct>any());
    doNothing().when(productRepository).flush();
    when(productRepository.findPhotoById(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);
    doNothing().when(photoStorageService).remove(Mockito.any());

    // Act
    photoProductCatalogService.remove(1L, 1L);

    // Assert that nothing has changed
    verify(productRepository).findPhotoById(Mockito.<Long>any(), Mockito.<Long>any());
    verify(productRepository).delete(Mockito.<PhotoProduct>any());
    verify(photoStorageService).remove(Mockito.any());
    verify(productRepository).flush();
  }

  /** Method under test: {@link PhotoProductCatalogService#remove(Long, Long)} */
  @Test
  void testRemove2() {
    // Arrange
    Address address = new Address();
    address.setCity(new City());
    address.setComplement("Complement");
    address.setDistrict("District");
    address.setNumber("42");
    address.setStreet("Street");
    address.setZipCode("21654");

    Cuisine cuisine = new Cuisine();
    cuisine.setId(1L);
    cuisine.setName("Name");
    cuisine.setRestaurant(new ArrayList<>());

    Restaurant restaurant = new Restaurant();
    restaurant.setActive(true);
    restaurant.setAddress(address);
    restaurant.setCuisine(cuisine);
    restaurant.setFreightRate(new BigDecimal("2.3"));
    restaurant.setId(1L);
    restaurant.setManagers(new HashSet<>());
    restaurant.setName("Name");
    restaurant.setOpenStatus(true);
    restaurant.setPaymentMethod(new HashSet<>());
    restaurant.setProducts(new ArrayList<>());
    restaurant.setRegistrationDate(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
    restaurant.setUpdateDate(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));

    Product product = new Product();
    product.setActive(true);
    product.setDescription("The characteristics of someone or something");
    product.setId(1L);
    product.setName("Name");
    product.setPrice(new BigDecimal("2.3"));
    product.setRestaurant(restaurant);

    PhotoProduct photoProduct = new PhotoProduct();
    photoProduct.setContentType("text/plain");
    photoProduct.setDescription("The characteristics of someone or something");
    photoProduct.setFilename("foo.txt");
    photoProduct.setFilesize(3L);
    photoProduct.setId(1L);
    photoProduct.setProduct(product);
    Optional<PhotoProduct> ofResult = Optional.of(photoProduct);
    doNothing().when(productRepository).delete(Mockito.<PhotoProduct>any());
    doNothing().when(productRepository).flush();
    when(productRepository.findPhotoById(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);
    doThrow(new PhotoProductNotFoundException("An error occurred"))
        .when(photoStorageService)
        .remove(Mockito.any());

    // Act and Assert
    assertThrows(
        PhotoProductNotFoundException.class, () -> photoProductCatalogService.remove(1L, 1L));
    verify(productRepository).findPhotoById(Mockito.<Long>any(), Mockito.<Long>any());
    verify(productRepository).delete(Mockito.<PhotoProduct>any());
    verify(photoStorageService).remove(Mockito.any());
    verify(productRepository).flush();
  }

  /** Method under test: {@link PhotoProductCatalogService#remove(Long, Long)} */
  @Test
  void testRemove3() {
    // Arrange
    Optional<PhotoProduct> emptyResult = Optional.empty();
    when(productRepository.findPhotoById(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(emptyResult);

    // Act and Assert
    assertThrows(
        PhotoProductNotFoundException.class, () -> photoProductCatalogService.remove(1L, 1L));
    verify(productRepository).findPhotoById(Mockito.<Long>any(), Mockito.<Long>any());
  }

  /** Method under test: {@link PhotoProductCatalogService#findOrElseThrow(Long, Long)} */
  @Test
  void testFindOrElseThrow() {
    // Arrange
    Address address = new Address();
    address.setCity(new City());
    address.setComplement("Complement");
    address.setDistrict("District");
    address.setNumber("42");
    address.setStreet("Street");
    address.setZipCode("21654");

    Cuisine cuisine = new Cuisine();
    cuisine.setId(1L);
    cuisine.setName("Name");
    cuisine.setRestaurant(new ArrayList<>());

    Restaurant restaurant = new Restaurant();
    restaurant.setActive(true);
    restaurant.setAddress(address);
    restaurant.setCuisine(cuisine);
    restaurant.setFreightRate(new BigDecimal("2.3"));
    restaurant.setId(1L);
    restaurant.setManagers(new HashSet<>());
    restaurant.setName("Name");
    restaurant.setOpenStatus(true);
    restaurant.setPaymentMethod(new HashSet<>());
    restaurant.setProducts(new ArrayList<>());
    restaurant.setRegistrationDate(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
    restaurant.setUpdateDate(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));

    Product product = new Product();
    product.setActive(true);
    product.setDescription("The characteristics of someone or something");
    product.setId(1L);
    product.setName("Name");
    product.setPrice(new BigDecimal("2.3"));
    product.setRestaurant(restaurant);

    PhotoProduct photoProduct = new PhotoProduct();
    photoProduct.setContentType("text/plain");
    photoProduct.setDescription("The characteristics of someone or something");
    photoProduct.setFilename("foo.txt");
    photoProduct.setFilesize(3L);
    photoProduct.setId(1L);
    photoProduct.setProduct(product);
    Optional<PhotoProduct> ofResult = Optional.of(photoProduct);
    when(productRepository.findPhotoById(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    // Act
    PhotoProduct actualFindOrElseThrowResult = photoProductCatalogService.findOrElseThrow(1L, 1L);

    // Assert
    verify(productRepository).findPhotoById(Mockito.<Long>any(), Mockito.<Long>any());
    assertSame(photoProduct, actualFindOrElseThrowResult);
  }

  /** Method under test: {@link PhotoProductCatalogService#findOrElseThrow(Long, Long)} */
  @Test
  void testFindOrElseThrow2() {
    // Arrange
    Optional<PhotoProduct> emptyResult = Optional.empty();
    when(productRepository.findPhotoById(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(emptyResult);

    // Act and Assert
    assertThrows(
        PhotoProductNotFoundException.class,
        () -> photoProductCatalogService.findOrElseThrow(1L, 1L));
    verify(productRepository).findPhotoById(Mockito.<Long>any(), Mockito.<Long>any());
  }
}
