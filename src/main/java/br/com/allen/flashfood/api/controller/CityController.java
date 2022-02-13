package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.StateNotFoundException;
import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.repository.CityRepository;
import br.com.allen.flashfood.domain.service.CityRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityRegistrationService cityRegistration;

    @GetMapping
    public List<City> getAllCity() {
        return cityRepository.findAll();
    }

    @GetMapping("/{cityId}")
    public City getCityById(@PathVariable Long cityId) {
        return cityRegistration.findCityOrElseThrow(cityId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public City addCity(@RequestBody @Valid City city) {
        try {
            return cityRegistration.saveCity(city);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cityId}")
    public City updateCity(@PathVariable Long cityId,
                           @RequestBody @Valid City city) {
        try {
            City actualCity = cityRegistration.findCityOrElseThrow(cityId);
            BeanUtils.copyProperties(city, actualCity, "id");
            return cityRegistration.saveCity(actualCity);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable Long cityId) {
        cityRegistration.deleteCity(cityId);
    }
}
