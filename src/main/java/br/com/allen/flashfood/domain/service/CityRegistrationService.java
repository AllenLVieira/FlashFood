package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.CityNotFoundException;
import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CityRegistrationService {
  public static final String CITY_IN_USE =
      "City with %d code cannot be removed because it is in use.";

  private final CityRepository cityRepository;
  private final StateRegistrationService stateRegistration;

  @Transactional
  public City saveCity(City city) {
    Long stateId = city.getState().getId();
    State state = stateRegistration.findStateOrElseThrow(stateId);
    city.setState(state);
    return cityRepository.save(city);
  }

  @Transactional
  public void deleteCity(Long cityId) {
    try {
      cityRepository.deleteById(cityId);
      cityRepository.flush();
    } catch (EmptyResultDataAccessException e) {
      throw new CityNotFoundException(cityId);
    } catch (DataIntegrityViolationException e) {
      throw new EntityInUseException(String.format(CITY_IN_USE, cityId));
    }
  }

  public City findCityOrElseThrow(Long cityId) {
    return cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
  }
}
