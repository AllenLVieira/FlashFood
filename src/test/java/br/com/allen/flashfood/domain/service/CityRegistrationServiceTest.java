package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.CityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {CityRegistrationService.class})
@ExtendWith(MockitoExtension.class)
class CityRegistrationServiceTest {

    @Autowired
    private CityRegistrationService cityRegistrationService;

    @MockBean
    private StateRegistrationService stateRegistrationService;

    @Mock
    private CityRepository cityRepository;
    private AutoCloseable autoCloseable;
    @Mock
    private StateRegistrationService stateService;
    private CityRegistrationService underTest;

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

    @Test
    @Disabled
    void deleteCity() {
    }

    @Test
    @Disabled
    void findCityOrElseThrow() {
    }
}