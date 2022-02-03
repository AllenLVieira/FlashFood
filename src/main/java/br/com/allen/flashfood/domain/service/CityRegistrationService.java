package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CityRegistrationService {
    public static final String CITY_NOT_FOUND = "There is no city register with code %d";
    public static final String CITY_IN_USE = "City with %d code cannot be removed because it is in use.";
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRegistrationService stateRegistration;

    public City saveCity(City city) {
        Long stateId = city.getState().getId();
        State state = stateRegistration.findStateOrElseThrow(stateId);
        city.setState(state);
        return cityRepository.save(city);
    }

    public void deleteCity(Long cityId) {
        try {
            cityRepository.deleteById(cityId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundedException(
                    String.format(CITY_NOT_FOUND, cityId)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(CITY_IN_USE, cityId)
            );
        }
    }

    public City findCityOrElseThrow(Long cityId){
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundedException(
                        String.format(CITY_NOT_FOUND, cityId)
                ));
    }
}
