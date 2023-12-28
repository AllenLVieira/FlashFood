package br.com.allen.flashfood.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.allen.flashfood.domain.exception.PaymentMethodNotFoundException;
import br.com.allen.flashfood.domain.exception.RestaurantNotFoundException;
import br.com.allen.flashfood.domain.exception.UserNotFoundException;
import br.com.allen.flashfood.domain.model.*;
import br.com.allen.flashfood.domain.repository.RestaurantRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RestaurantRegistrationService.class})
@ExtendWith(SpringExtension.class)
class RestaurantRegistrationServiceTest {

    @Autowired
    private RestaurantRegistrationService underTest;

    @MockBean
    private CityRegistrationService cityRegistrationService;

    @MockBean
    private CuisineRegistrationService cuisineRegistrationService;

    @MockBean
    private PaymentMethodRegistrationService paymentMethodRegistrationService;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @MockBean
    private UserRegistrationsService userRegistrationsService;

    private Restaurant restaurant;
    private Cuisine cuisine;
    private Address address;

    @BeforeEach
    void setUp() {
        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city = new City();
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
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#saveRestaurant(Restaurant)}
     */
    @Test
    void shouldSucessfullySaveRestaurant() {
        when(restaurantRepository.save(any())).thenReturn(restaurant);
        Cuisine newCuisine = new Cuisine();
        newCuisine.setId(123L);
        newCuisine.setName("Name");
        newCuisine.setRestaurant(new ArrayList<>());

        when(cuisineRegistrationService.findCuisineOrElseThrow(any())).thenReturn(newCuisine);
        State newState = new State();
        newState.setId(123L);
        newState.setName("Name");

        City newCity = new City();
        newCity.setId(123L);
        newCity.setName("Name");
        newCity.setState(newState);
        when(cityRegistrationService.findCityOrElseThrow(any())).thenReturn(newCity);

        Address newAddress = new Address();
        newAddress.setCity(newCity);
        newAddress.setComplement("Complement");
        newAddress.setDistrict("District");
        newAddress.setNumber("42");
        newAddress.setStreet("Street");
        newAddress.setZipCode("21654");

        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setActive(true);
        newRestaurant.setAddress(newAddress);
        newRestaurant.setCuisine(newCuisine);
        newRestaurant.setFreightRate(BigDecimal.valueOf(42L));
        newRestaurant.setId(123L);
        newRestaurant.setManagers(new HashSet<>());
        newRestaurant.setName("Name");
        newRestaurant.setOpenStatus(true);
        newRestaurant.setPaymentMethod(new HashSet<>());
        newRestaurant.setProducts(new ArrayList<>());
        newRestaurant.setRegistrationDate(null);
        newRestaurant.setUpdateDate(null);
        Restaurant actualSaveRestaurantResult = underTest.saveRestaurant(newRestaurant);
        assertSame(restaurant, actualSaveRestaurantResult);
        assertEquals("42", actualSaveRestaurantResult.getFreightRate().toString());
        verify(restaurantRepository).save(any());
        verify(cuisineRegistrationService).findCuisineOrElseThrow(any());
        verify(cityRegistrationService).findCityOrElseThrow(any());
        assertEquals(newCuisine, newRestaurant.getCuisine());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#saveRestaurant(Restaurant)}
     */
    @Test
    void shouldThrowRestaurantNotFoundExceptionWhenTryingToSave() {
        when(restaurantRepository.save(any())).thenReturn(restaurant);

        Cuisine newCuisine = new Cuisine();
        newCuisine.setId(123L);
        newCuisine.setName("Name");
        newCuisine.setRestaurant(new ArrayList<>());
        when(cuisineRegistrationService.findCuisineOrElseThrow(any())).thenReturn(newCuisine);
        when(cityRegistrationService.findCityOrElseThrow(any()))
                .thenThrow(new RestaurantNotFoundException("An error occurred"));

        State newState = new State();
        newState.setId(123L);
        newState.setName("Name");

        City newCity = new City();
        newCity.setId(123L);
        newCity.setName("Name");
        newCity.setState(newState);

        Address newAddress = new Address();
        newAddress.setCity(newCity);
        newAddress.setComplement("Complement");
        newAddress.setDistrict("District");
        newAddress.setNumber("42");
        newAddress.setStreet("Street");
        newAddress.setZipCode("21654");

        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setActive(true);
        newRestaurant.setAddress(newAddress);
        newRestaurant.setCuisine(newCuisine);
        newRestaurant.setFreightRate(BigDecimal.valueOf(42L));
        newRestaurant.setId(123L);
        newRestaurant.setManagers(new HashSet<>());
        newRestaurant.setName("Name");
        newRestaurant.setOpenStatus(true);
        newRestaurant.setPaymentMethod(new HashSet<>());
        newRestaurant.setProducts(new ArrayList<>());
        newRestaurant.setRegistrationDate(null);
        newRestaurant.setUpdateDate(null);
        assertThrows(RestaurantNotFoundException.class, () -> underTest.saveRestaurant(newRestaurant));
        verify(cuisineRegistrationService).findCuisineOrElseThrow(any());
        verify(cityRegistrationService).findCityOrElseThrow(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#activateRestaurant(Long)}
     */
    @Test
    void shouldActivateRestaurant() {
        restaurant.setActive(false);
        Optional<Restaurant> ofResult = Optional.of(restaurant);
        when(restaurantRepository.findById(any())).thenReturn(ofResult);
        underTest.activateRestaurant(123L);
        verify(restaurantRepository).findById(any());
        assertTrue(restaurant.getActive());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#activateRestaurant(Long)}
     */
    @Test
    void shouldThrowRestaurantNotFoundExceptionWhenActivate() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class, () -> underTest.activateRestaurant(123L));
        verify(restaurantRepository).findById(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#disableRestaurant(Long)}
     */
    @Test
    void shouldDeactivateRestaurantWithSuccess() {
        Optional<Restaurant> ofResult = Optional.of(restaurant);
        when(restaurantRepository.findById(any())).thenReturn(ofResult);
        underTest.disableRestaurant(123L);
        assertFalse(restaurant.getActive());
        verify(restaurantRepository).findById(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#disableRestaurant(Long)}
     */
    @Test
    void shouldThrowRestaurantNotFoundExceptionWhenDisabled() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class, () -> underTest.disableRestaurant(123L));
        verify(restaurantRepository).findById(any());
    }


    /**
     * Method under test: {@link RestaurantRegistrationService#massActivateRestaurant(List)}
     */
    @Test
    void shouldMassActivateRestaurant() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setActive(true);
        restaurant1.setAddress(address);
        restaurant1.setCuisine(cuisine);
        restaurant1.setFreightRate(BigDecimal.valueOf(42L));
        restaurant1.setId(1L);
        restaurant1.setManagers(new HashSet<>());
        restaurant1.setName("Name");
        restaurant1.setOpenStatus(true);
        restaurant1.setPaymentMethod(new HashSet<>());
        restaurant1.setProducts(new ArrayList<>());
        restaurant1.setRegistrationDate(null);
        restaurant1.setUpdateDate(null);
        restaurant1.setActive(false);
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setActive(true);
        restaurant2.setAddress(address);
        restaurant2.setCuisine(cuisine);
        restaurant2.setFreightRate(BigDecimal.valueOf(42L));
        restaurant2.setId(2L);
        restaurant2.setManagers(new HashSet<>());
        restaurant2.setName("Name");
        restaurant2.setOpenStatus(true);
        restaurant2.setPaymentMethod(new HashSet<>());
        restaurant2.setProducts(new ArrayList<>());
        restaurant2.setRegistrationDate(null);
        restaurant2.setUpdateDate(null);
        restaurant2.setActive(false);

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(restaurant1.getId());
        resultLongList.add(restaurant2.getId());

        when(restaurantRepository.findById(restaurant1.getId())).thenReturn(Optional.of(restaurant1));
        when(restaurantRepository.findById(restaurant2.getId())).thenReturn(Optional.of(restaurant2));

        underTest.massActivateRestaurant(resultLongList);
        assertTrue(restaurant.getActive());
        assertTrue(restaurant2.getActive());
        verify(restaurantRepository, times(2)).findById(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#massActivateRestaurant(List)}
     */
    @Test
    void shouldThrowRestaurantNotFoundExceptionWhenActivateUnexistentRestaurant() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());
        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(1L);
        assertThrows(RestaurantNotFoundException.class,
                () -> underTest.massActivateRestaurant(resultLongList));
        verify(restaurantRepository).findById(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#massDisableRestaurant(List)}
     */
    @Test
    void shouldSuccessfullyDisableRestaurant() {
        restaurant.setActive(true);
        Optional<Restaurant> ofResult = Optional.of(restaurant);
        when(restaurantRepository.findById(any())).thenReturn(ofResult);

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(123L);
        underTest.massDisableRestaurant(resultLongList);
        verify(restaurantRepository).findById(any());
        assertFalse(restaurant.getActive());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#massDisableRestaurant(List)}
     */
    @Test
    void shouldThrowRestaurantNotFoundExceptionWhenMassDisableRestaurant() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(1L);
        assertThrows(RestaurantNotFoundException.class,
                () -> underTest.massDisableRestaurant(resultLongList));
        verify(restaurantRepository).findById(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#removePaymentMethod(Long, Long)}
     */
    @Test
    void shouldSuccessfullyRemovePaymentMethod() {
        Optional<Restaurant> ofResult = Optional.of(restaurant);
        when(restaurantRepository.findById(any())).thenReturn(ofResult);

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("Description");
        paymentMethod.setId(123L);

        restaurant.addPaymentMethod(paymentMethod);
        when(paymentMethodRegistrationService.findPaymentMethodOrElseThrow(any())).thenReturn(paymentMethod);
        underTest.removePaymentMethod(123L, 123L);
        verify(restaurantRepository).findById(any());
        verify(paymentMethodRegistrationService).findPaymentMethodOrElseThrow(any());
        assertFalse(restaurant.getPaymentMethod().contains(paymentMethod));
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#removePaymentMethod(Long, Long)}
     */
    @Test
    void removePaymentMethodShouldThrowPaymentMethodNotFoundExceptionWhenPaymentDoesNotExists() {
        Optional<Restaurant> ofResult = Optional.of(restaurant);
        when(restaurantRepository.findById(any())).thenReturn(ofResult);
        when(paymentMethodRegistrationService.findPaymentMethodOrElseThrow(any()))
                .thenThrow(new PaymentMethodNotFoundException("An error occurred"));
        assertThrows(PaymentMethodNotFoundException.class,
                () -> underTest.removePaymentMethod(123L, 123L));
        verify(restaurantRepository).findById(any());
        verify(paymentMethodRegistrationService).findPaymentMethodOrElseThrow(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#removePaymentMethod(Long, Long)}
     */
    @Test
    void removePaymentMethodShouldThrowRestaurantNotFoundExceptionWhenPaymentDoesNotExists() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("Description");
        paymentMethod.setId(123L);
        when(paymentMethodRegistrationService.findPaymentMethodOrElseThrow(any())).thenReturn(paymentMethod);
        assertThrows(RestaurantNotFoundException.class,
                () -> underTest.removePaymentMethod(123L, 123L));
        verify(restaurantRepository).findById(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#addPaymentMethod(Long, Long)}
     */
    @Test
    void addPaymentShouldThrowPaymentMethodNotFoundExceptionWhenPaymentDoesNotExists() {
        Optional<Restaurant> ofResult = Optional.of(restaurant);
        when(restaurantRepository.findById(any())).thenReturn(ofResult);
        when(paymentMethodRegistrationService.findPaymentMethodOrElseThrow(any()))
                .thenThrow(new PaymentMethodNotFoundException("An error occurred"));
        assertThrows(PaymentMethodNotFoundException.class, () -> underTest.addPaymentMethod(123L, 123L));
        verify(restaurantRepository).findById(any());
        verify(paymentMethodRegistrationService).findPaymentMethodOrElseThrow(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#addPaymentMethod(Long, Long)}
     */
    @Test
    void shouldAddPaymentMethodToRestaurant() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("Description");
        paymentMethod.setId(123L);

        Optional<Restaurant> ofResult = Optional.of(restaurant);
        when(restaurantRepository.findById(any())).thenReturn(ofResult);
        when(paymentMethodRegistrationService.findPaymentMethodOrElseThrow(any())).thenReturn(paymentMethod);
        underTest.addPaymentMethod(123L, 123L);
        verify(restaurantRepository).findById(any());
        verify(paymentMethodRegistrationService).findPaymentMethodOrElseThrow(any());
        assertTrue(restaurant.getPaymentMethod().contains(paymentMethod));
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#addPaymentMethod(Long, Long)}
     */
    @Test
    void addPaymentShouldThrowRestaurantNotFoundExceptionWhenEmptyRestaurant() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("Description");
        paymentMethod.setId(123L);
        when(paymentMethodRegistrationService.findPaymentMethodOrElseThrow(any())).thenReturn(paymentMethod);
        assertThrows(RestaurantNotFoundException.class, () -> underTest.addPaymentMethod(123L, 123L));
        verify(restaurantRepository).findById(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#openRestaurant(Long)}
     */
    @Test
    void openRestaurantShouldSuccess() {
        restaurant.setOpenStatus(false);
        Optional<Restaurant> ofResult = Optional.of(restaurant);
        when(restaurantRepository.findById(any())).thenReturn(ofResult);
        underTest.openRestaurant(123L);
        assertTrue(restaurant.getOpenStatus());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#openRestaurant(Long)}
     */
    @Test
    void openRestaurantShouldThrowRestaurantNotFoundExceptionWhenEmptyRestaurant() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class, () -> underTest.openRestaurant(123L));
        verify(restaurantRepository).findById(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#closeRestaurant(Long)}
     */
    @Test
    void closeRestaurantShouldSuccess() {
        restaurant.setOpenStatus(true);
        Optional<Restaurant> ofResult = Optional.of(restaurant);
        when(restaurantRepository.findById(any())).thenReturn(ofResult);
        underTest.closeRestaurant(123L);
        assertFalse(restaurant.getOpenStatus());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#closeRestaurant(Long)}
     */
    @Test
    void closeRestaurantShouldThrowRestaurantNotFoundExceptionWhenEmptyRestaurant() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class, () -> underTest.closeRestaurant(123L));
        verify(restaurantRepository).findById(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#unlinkManager(Long, Long)}
     */
    @Test
    void unlinkManagerShouldThrowUserNotFoundExceptionWhenDontExistsUser() {
        Optional<Restaurant> ofResult = Optional.of(restaurant);
        when(restaurantRepository.findById(any())).thenReturn(ofResult);
        when(userRegistrationsService.findUserOrElseThrow(any()))
                .thenThrow(new UserNotFoundException("An error occurred"));
        assertThrows(UserNotFoundException.class, () -> underTest.unlinkManager(123L, 123L));
        verify(restaurantRepository).findById(any());
        verify(userRegistrationsService).findUserOrElseThrow(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#unlinkManager(Long, Long)}
     */
    @Test
    void shouldRemoveManagerFromRestaurant() {
        Optional<Restaurant> ofResult = Optional.of(restaurant);
        when(restaurantRepository.findById(any())).thenReturn(ofResult);

        User user = new User();
        user.setEmail("allenvieira96@gmail.com");
        user.setGroups(new HashSet<>());
        user.setId(123L);
        user.setName("Allen");
        user.setPassword("123456");
        user.setRegistrationDate(null);
        restaurant.getManagers().add(user);
        when(userRegistrationsService.findUserOrElseThrow(any())).thenReturn(user);
        underTest.unlinkManager(123L, 123L);
        assertTrue(restaurant.getManagers().isEmpty());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#linkManager(Long, Long)}
     */
    @Test
    void linkManagerShouldThrowUserNotFoundExceptionWhenDontExistsUser() {
        Optional<Restaurant> ofResult = Optional.of(restaurant);
        when(restaurantRepository.findById(any())).thenReturn(ofResult);
        when(userRegistrationsService.findUserOrElseThrow(any()))
                .thenThrow(new UserNotFoundException("An error occurred"));
        assertThrows(UserNotFoundException.class, () -> underTest.linkManager(123L, 123L));
        verify(restaurantRepository).findById(any());
        verify(userRegistrationsService).findUserOrElseThrow(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#linkManager(Long, Long)}
     */
    @Test
    void shouldAddUserAsManagerToRestaurant() {
        Optional<Restaurant> ofResult = Optional.of(restaurant);
        when(restaurantRepository.findById(any())).thenReturn(ofResult);

        User user = new User();
        user.setEmail("allenvieira96@gmail.com");
        user.setGroups(new HashSet<>());
        user.setId(123L);
        user.setName("Allen");
        user.setPassword("123456");
        user.setRegistrationDate(null);
        when(userRegistrationsService.findUserOrElseThrow(any())).thenReturn(user);
        underTest.linkManager(123L, 123L);
        assertTrue(restaurant.getManagers().contains(user));
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#linkManager(Long, Long)}
     */
    @Test
    void linkManagerShouldThrowRestaurantNotFoundExceptionWhenEmptyRestaurant() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());

        User user = new User();
        user.setEmail("allenvieira96@gmail.com");
        user.setGroups(new HashSet<>());
        user.setId(123L);
        user.setName("Allen");
        user.setPassword("123456");
        user.setRegistrationDate(null);
        restaurant.getManagers().add(user);
        when(userRegistrationsService.findUserOrElseThrow(any())).thenReturn(user);
        assertThrows(RestaurantNotFoundException.class, () -> underTest.linkManager(123L, 123L));
        verify(restaurantRepository).findById(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#findRestaurantOrElseThrow(Long)}
     */
    @Test
    void shouldSuccessfullyFindRestaurant() {
        Optional<Restaurant> ofResult = Optional.of(restaurant);
        when(restaurantRepository.findById(any())).thenReturn(ofResult);
        Restaurant actualFindRestaurantOrElseThrowResult = underTest.findRestaurantOrElseThrow(123L);
        assertSame(restaurant, actualFindRestaurantOrElseThrowResult);
        assertEquals("42", actualFindRestaurantOrElseThrowResult.getFreightRate().toString());
        verify(restaurantRepository).findById(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#findRestaurantOrElseThrow(Long)}
     */
    @Test
    void shouldThrowRestaurantNotFoundExceptionWhenEmptyRestaurant() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class,
                () -> underTest.findRestaurantOrElseThrow(123L));
        verify(restaurantRepository).findById(any());
    }

    /**
     * Method under test: {@link RestaurantRegistrationService#findRestaurantOrElseThrow(Long)}
     */
    @Test
    void shouldThrowRestaurantNotFoundExceptionWhenFindById() {
        when(restaurantRepository.findById(any())).thenThrow(new RestaurantNotFoundException("An error occurred"));
        assertThrows(RestaurantNotFoundException.class,
                () -> underTest.findRestaurantOrElseThrow(123L));
        verify(restaurantRepository).findById(any());
    }
}

