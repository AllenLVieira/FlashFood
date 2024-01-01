package br.com.allen.flashfood.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.allen.flashfood.api.assembler.RestaurantModelAssembler;
import br.com.allen.flashfood.api.assembler.RestaurantRequestDisassembler;
import br.com.allen.flashfood.api.model.request.RestaurantRequest;
import br.com.allen.flashfood.api.model.response.RestaurantResponse;
import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.CuisineNotFoundException;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.repository.RestaurantRepository;
import br.com.allen.flashfood.domain.service.RestaurantRegistrationService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.MappingJacksonValue;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {
  @Mock private RestaurantRepository restaurantRepository;

  @Mock private RestaurantRegistrationService restaurantRegistration;

  @Mock private RestaurantModelAssembler restaurantModelAssembler;

  @Mock private RestaurantRequestDisassembler requestDisassembler;

  @InjectMocks private RestaurantController restaurantController;

  @Test
  void getAllRestaurants() {
    // Arrange
    List<Restaurant> restaurantList = new ArrayList<>(); // Add mock data if necessary
    when(restaurantRepository.findAll()).thenReturn(restaurantList);
    List<RestaurantResponse> restaurantResponses = new ArrayList<>(); // Add mock data if necessary
    when(restaurantModelAssembler.toCollectionModel(restaurantList))
        .thenReturn(restaurantResponses);

    // Act
    MappingJacksonValue result = restaurantController.getAllRestaurants(null);

    // Assert
    verify(restaurantRepository).findAll();
    assertNotNull(result);
  }

  @Test
  void getRestaurantById() {
    // Arrange
    Long restaurantId = 1L;
    Restaurant mockRestaurant = mock(Restaurant.class);
    when(restaurantRegistration.findRestaurantOrElseThrow(restaurantId)).thenReturn(mockRestaurant);
    RestaurantResponse mockResponse = mock(RestaurantResponse.class);
    when(restaurantModelAssembler.toModel(mockRestaurant)).thenReturn(mockResponse);

    // Act
    RestaurantResponse result = restaurantController.getRestaurantById(restaurantId);

    // Assert
    verify(restaurantRegistration).findRestaurantOrElseThrow(restaurantId);
    assertEquals(mockResponse, result);
  }

  @Test
  void addRestaurant() {
    // Arrange
    RestaurantRequest restaurantRequest = mock(RestaurantRequest.class);
    Restaurant mockRestaurant = mock(Restaurant.class);
    when(requestDisassembler.toDomainObject(restaurantRequest)).thenReturn(mockRestaurant);
    when(restaurantRegistration.saveRestaurant(mockRestaurant)).thenReturn(mockRestaurant);
    RestaurantResponse mockResponse = mock(RestaurantResponse.class);
    when(restaurantModelAssembler.toModel(mockRestaurant)).thenReturn(mockResponse);

    // Act
    RestaurantResponse result = restaurantController.addRestaurant(restaurantRequest);

    // Assert
    verify(requestDisassembler).toDomainObject(restaurantRequest);
    verify(restaurantRegistration).saveRestaurant(mockRestaurant);
    assertEquals(mockResponse, result);
  }

  @Test
  void activateRestaurant() {
    // Arrange
    Long restaurantId = 1L;

    // Act
    restaurantController.activateRestaurant(restaurantId);

    // Assert
    verify(restaurantRegistration).activateRestaurant(restaurantId);
  }

  @Test
  void disableRestaurant() {
    // Arrange
    Long restaurantId = 1L;

    // Act
    restaurantController.disableRestaurant(restaurantId);

    // Assert
    verify(restaurantRegistration).disableRestaurant(restaurantId);
  }

  @Test
  void massActivateRestaurant() {
    // Arrange
    List<Long> restaurantIds = List.of(1L, 2L);

    // Act
    restaurantController.massActivateRestaurant(restaurantIds);

    // Assert
    verify(restaurantRegistration).massActivateRestaurant(restaurantIds);
  }

  @Test
  void massDisableRestaurant() {
    // Arrange
    List<Long> restaurantIds = List.of(1L, 2L);

    // Act
    restaurantController.massDisableRestaurant(restaurantIds);

    // Assert
    verify(restaurantRegistration).massDisableRestaurant(restaurantIds);
  }

  @Test
  void openRestaurant() {
    // Arrange
    Long restaurantId = 1L;

    // Act
    restaurantController.openRestaurant(restaurantId);

    // Assert
    verify(restaurantRegistration).openRestaurant(restaurantId);
  }

  @Test
  void closeRestaurant() {
    // Arrange
    Long restaurantId = 1L;

    // Act
    restaurantController.closeRestaurant(restaurantId);

    // Assert
    verify(restaurantRegistration).closeRestaurant(restaurantId);
  }

  @Test
  void updateRestaurantSuccess() {
    // Arrange
    Long restaurantId = 1L;
    RestaurantRequest restaurantRequest = mock(RestaurantRequest.class);
    Restaurant mockRestaurant = mock(Restaurant.class);
    RestaurantResponse mockResponse = mock(RestaurantResponse.class);

    when(restaurantRegistration.findRestaurantOrElseThrow(restaurantId)).thenReturn(mockRestaurant);
    when(restaurantRegistration.saveRestaurant(mockRestaurant)).thenReturn(mockRestaurant);
    when(restaurantModelAssembler.toModel(mockRestaurant)).thenReturn(mockResponse);

    // Act
    RestaurantResponse response =
        restaurantController.updateRestaurant(restaurantId, restaurantRequest);

    // Assert
    verify(restaurantRegistration).findRestaurantOrElseThrow(restaurantId);
    verify(requestDisassembler).copyToDomainObject(restaurantRequest, mockRestaurant);
    verify(restaurantRegistration).saveRestaurant(mockRestaurant);
    assertEquals(mockResponse, response);
  }

  @Test
  void updateRestaurantThrowsBusinessException() {
    // Arrange
    Long restaurantId = 1L;
    RestaurantRequest restaurantRequest = mock(RestaurantRequest.class);
    Restaurant mockRestaurant = mock(Restaurant.class);

    when(restaurantRegistration.findRestaurantOrElseThrow(restaurantId)).thenReturn(mockRestaurant);

    // Use doThrow() with the specific instance instead of any()
    doThrow(new CuisineNotFoundException("error"))
        .when(requestDisassembler)
        .copyToDomainObject(eq(restaurantRequest), eq(mockRestaurant));

    // Act & Assert
    assertThrows(
        BusinessException.class,
        () -> restaurantController.updateRestaurant(restaurantId, restaurantRequest));
  }
}
