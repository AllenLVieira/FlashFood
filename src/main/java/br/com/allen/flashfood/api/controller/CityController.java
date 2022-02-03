package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.repository.CityRepository;
import br.com.allen.flashfood.domain.service.CityRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public City addCity(@RequestBody City city) {
        return cityRegistration.saveCity(city);
    }

    @PutMapping("/{cityId}")
    public City updateCity(@PathVariable Long cityId,
                                           @RequestBody City city) {
        City actualCity = cityRegistration.findCityOrElseThrow(cityId);
        BeanUtils.copyProperties(city, actualCity, "id");
        return cityRegistration.saveCity(actualCity);
    }

    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable Long cityId) {
        cityRegistration.deleteCity(cityId);
    }
}
