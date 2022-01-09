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

@RestController
@RequestMapping("/cities")
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityRegistrationService cityRegistration;

    @GetMapping
    public List<City> getAllCity() {
        return cityRepository.getAllCities();
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<City> getCityById(@PathVariable Long cityId) {
        City city = cityRepository.getCityById(cityId);
        if (city != null) {
            return ResponseEntity.ok(city);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public City addCity(@RequestBody City city) {
        return cityRegistration.saveCity(city);
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<City> updateCity(@PathVariable Long cityId,
                                           @RequestBody City city) {
        City actualCity = cityRepository.getCityById(cityId);
        if (actualCity != null) {
            BeanUtils.copyProperties(city, actualCity, "id");
            actualCity = cityRegistration.saveCity(actualCity);
            return ResponseEntity.ok(actualCity);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<?> deleteCity(@PathVariable Long cityId) {
        try {
            cityRegistration.deleteCity(cityId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundedException e) {
            return ResponseEntity.notFound().build();
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
}
