package br.com.allen.flashfood.api.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;

import br.com.allen.flashfood.api.assembler.PhotoProductModelAssembler;
import br.com.allen.flashfood.api.model.request.ProductPhotoRequest;
import br.com.allen.flashfood.api.model.response.PhotoProductResponse;
import br.com.allen.flashfood.domain.exception.PhotoProductNotFoundException;
import br.com.allen.flashfood.domain.model.PhotoProduct;
import br.com.allen.flashfood.domain.model.Product;
import br.com.allen.flashfood.domain.service.PhotoProductCatalogService;
import br.com.allen.flashfood.domain.service.PhotoStorageService;
import br.com.allen.flashfood.domain.service.ProductRegistrationService;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

class RestaurantProductPhotoControllerTest {

  @Mock private PhotoProductCatalogService photoProductCatalogService;

  @Mock private ProductRegistrationService productRegistrationService;

  @Mock private PhotoProductModelAssembler photoProductModelAssembler;

  @Mock private PhotoStorageService photoStorageService;

  @InjectMocks private RestaurantProductPhotoController controller;

  // Setup method to initialize mocks
  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testUpdatePhoto() throws Exception {
    // Mock the dependencies
    MockMultipartFile file =
        new MockMultipartFile("file", "filename.jpg", "image/jpeg", "some-image".getBytes());
    ProductPhotoRequest request = new ProductPhotoRequest(file, "description");
    Product product = new Product(); // Assuming you have a Product class
    PhotoProduct photo = new PhotoProduct();

    // Define behavior of mocked services
    when(productRegistrationService.findProductOrElseThrow(anyLong(), anyLong()))
        .thenReturn(product);
    when(photoProductCatalogService.save(any(PhotoProduct.class), any())).thenReturn(photo);
    when(photoProductModelAssembler.toModel(photo)).thenReturn(new PhotoProductResponse());

    // Call the method to test
    PhotoProductResponse response = controller.updatePhoto(1L, 1L, request);

    // Assertions to verify the expected behavior
    assertNotNull(response);
    verify(photoProductCatalogService).save(any(PhotoProduct.class), any());
  }

  @Test
  void testFind() {
    // Mock the dependencies
    PhotoProduct photo = new PhotoProduct();
    when(photoProductCatalogService.findOrElseThrow(anyLong(), anyLong())).thenReturn(photo);
    when(photoProductModelAssembler.toModel(photo)).thenReturn(new PhotoProductResponse());

    // Call the method to test
    PhotoProductResponse response = controller.find(1L, 1L);

    // Assertions
    assertNotNull(response);
    verify(photoProductCatalogService).findOrElseThrow(1L, 1L);
  }

  @Test
  void testGetPhoto() throws Exception {
    // Arrange
    Long restaurantId = 1L;
    Long productId = 1L;
    PhotoProduct photo = new PhotoProduct();
    photo.setContentType("image/jpeg");
    photo.setFilename("test.jpg");

    ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[0]);
    PhotoStorageService.RetrievedPhoto retrievedPhoto =
        mock(PhotoStorageService.RetrievedPhoto.class);
    when(retrievedPhoto.getInputStream()).thenReturn(inputStream);
    when(retrievedPhoto.urlExists()).thenReturn(false); // Assuming the URL does not exist

    when(photoProductCatalogService.findOrElseThrow(restaurantId, productId)).thenReturn(photo);
    when(photoStorageService.retrieve(photo.getFilename())).thenReturn(retrievedPhoto);

    // Act
    ResponseEntity<?> response = controller.getPhoto(restaurantId, productId, "image/jpeg");

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(photoStorageService).retrieve("test.jpg");
  }

  @Test
  void testGetPhoto_EntityNotFound() throws HttpMediaTypeNotAcceptableException {
    // Arrange
    Long restaurantId = 1L;
    Long productId = 1L;
    String acceptHeader = "image/jpeg";

    when(photoProductCatalogService.findOrElseThrow(restaurantId, productId))
        .thenThrow(new PhotoProductNotFoundException("Entity not found"));

    // Act
    ResponseEntity<?> response = controller.getPhoto(restaurantId, productId, acceptHeader);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void testGetPhoto_IncompatibleMediaType() {
    // Arrange
    Long restaurantId = 1L;
    Long productId = 1L;
    PhotoProduct photo = new PhotoProduct();
    photo.setContentType("image/png"); // Set a content type that is not compatible
    photo.setFilename("test.png");

    PhotoStorageService.RetrievedPhoto retrievedPhoto =
        mock(PhotoStorageService.RetrievedPhoto.class);
    when(photoProductCatalogService.findOrElseThrow(restaurantId, productId)).thenReturn(photo);
    when(photoStorageService.retrieve(anyString())).thenReturn(retrievedPhoto);

    String acceptHeader =
        "image/jpeg"; // Accept header that is not compatible with photo's content type

    // Act & Assert
    Exception exception =
        assertThrows(
            HttpMediaTypeNotAcceptableException.class,
            () -> controller.getPhoto(restaurantId, productId, acceptHeader));

    // Verify that the exception message contains the expected content
    String expectedMessage =
        "MediaType image/png is not acceptable. Acceptable types: [image/jpeg]";
    assertTrue(exception.getMessage().contains(expectedMessage));
  }

  @Test
  void testGetPhoto_UrlExists() throws HttpMediaTypeNotAcceptableException {
    // Arrange
    Long restaurantId = 1L;
    Long productId = 1L;
    PhotoProduct photo = new PhotoProduct();
    photo.setContentType("image/jpeg");
    photo.setFilename("test.jpg");

    PhotoStorageService.RetrievedPhoto retrievedPhoto =
        mock(PhotoStorageService.RetrievedPhoto.class);
    when(retrievedPhoto.urlExists()).thenReturn(true);
    when(retrievedPhoto.getUrl()).thenReturn("http://example.com/photo.jpg");

    when(photoProductCatalogService.findOrElseThrow(restaurantId, productId)).thenReturn(photo);
    when(photoStorageService.retrieve(photo.getFilename())).thenReturn(retrievedPhoto);

    // Act
    ResponseEntity<?> response = controller.getPhoto(restaurantId, productId, "image/jpeg");

    // Assert
    assertEquals(HttpStatus.FOUND, response.getStatusCode());
    assertEquals(
        "http://example.com/photo.jpg", response.getHeaders().getFirst(HttpHeaders.LOCATION));
  }

  @Test
  void remove_ShouldCallService() {
    // Arrange
    Long restaurantId = 1L;
    Long productId = 1L;
    doNothing().when(photoProductCatalogService).remove(restaurantId, productId);

    // Act
    controller.remove(restaurantId, productId);

    // Assert
    verify(photoProductCatalogService).remove(restaurantId, productId);
  }
}
