package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.CityRepository;
import br.com.allen.flashfood.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CityRegistrationService {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    public City saveCity(City city) {
        Long stateId = city.getState().getId();
        State state = stateRepository.getStateById(stateId);
        if (state == null) {
            throw new EntityNotFoundedException(
                    String.format("There is no state registration with code %d", stateId)
            );
        }
        city.setState(state);
        return cityRepository.saveCity(city);
    }

    public void deleteCity(Long cityId) {
        try {
            cityRepository.removeCity(cityId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundedException(
                    String.format("There is no city register with code %d", cityId)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("City with %d code cannot be removed because it is in use.", cityId)
            );
        }
    }
}
