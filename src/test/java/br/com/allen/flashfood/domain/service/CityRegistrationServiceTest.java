package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.CityNotFoundException;
import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.CityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CityRegistrationService.class})
@ExtendWith(MockitoExtension.class)
class CityRegistrationServiceTest {

    @Autowired
    private CityRegistrationService underTest;

    @Mock
    private CityRepository cityRepository;

    private AutoCloseable autoCloseable;
    @Mock
    private StateRegistrationService stateService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CityRegistrationService(cityRepository, stateService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canSaveCity() {
        //given
        State state = new State();
        state.setId(1L);
        state.setName("FooState");
        City city = new City();
        city.setId(1L);
        city.setName("FooCity");
        city.setState(state);

        //when
        underTest.saveCity(city);

        //then
        ArgumentCaptor<City> cityArgumentCaptor = ArgumentCaptor.forClass(City.class);
        verify(cityRepository).save(cityArgumentCaptor.capture());
        City capturedCity = cityArgumentCaptor.getValue();
        assertThat(capturedCity).isEqualTo(city);

    }

    /**
     * Method under test: {@link CityRegistrationService#deleteCity(Long)}}
     */
    @Test
    void shouldThrowsCityNotFoundExceptionWhenDeleteCity() {
        doThrow(new EmptyResultDataAccessException(3)).when(cityRepository).deleteById(any());
        assertThrows(CityNotFoundException.class, () -> underTest.deleteCity(123L));
        verify(cityRepository).deleteById(any());
    }

    /**
     * Method under test: {@link CityRegistrationService#findCityOrElseThrow(Long)}
     */
    @Test
    void shouldThrowsEntityInUseExceptionWhenDeleteCity() {
        doThrow(new DataIntegrityViolationException("An error occured")).when(cityRepository).deleteById(any());
        assertThrows(EntityInUseException.class, () -> underTest.deleteCity(123L));
        verify(cityRepository).deleteById(any());
    }

    /**
     * Method under test: {@link CityRegistrationService#deleteCity(Long)}
     */
    @Test
    void shouldDeleteCity() {
        doNothing().when(cityRepository).flush();
        doNothing().when(cityRepository).deleteById(any());
        underTest.deleteCity(123L);
        verify(cityRepository).flush();
        verify(cityRepository).deleteById(any());
    }

    /**
     * Method under test: {@link CityRegistrationService#findCityOrElseThrow(Long)}
     */
    @Test
    void shouldFindCity() {
        //given
        State state = new State();
        state.setId(1L);
        state.setName("FooState");
        City city = new City();
        city.setId(1L);
        city.setName("FooCity");
        city.setState(state);
        Optional<City> ofResult = Optional.of(city);
        when(cityRepository.findById(any())).thenReturn(ofResult);
        assertSame(city, underTest.findCityOrElseThrow(123L));
        verify(cityRepository).findById(any());
    }

    /**
     * Method under test: {@link CityRegistrationService#findCityOrElseThrow(Long)}
     */
    @Test
    void shouldThrowCityNotFoundExceptionWhenCityEmpty() {
        when(cityRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(CityNotFoundException.class,
                () -> underTest.findCityOrElseThrow(123L));
        verify(cityRepository).findById(any());
    }

    /**
     * Method under test: {@link CityRegistrationService#findCityOrElseThrow(Long)}
     */
    @Test
    void shouldThrowEmptyResultDataAccessExceptionWhenCityNotExists() {
        when(cityRepository.findById(any())).thenThrow(new EmptyResultDataAccessException(3));
        assertThrows(EmptyResultDataAccessException.class,
                () -> underTest.findCityOrElseThrow(123L));
        verify(cityRepository).findById(any());
    }
}