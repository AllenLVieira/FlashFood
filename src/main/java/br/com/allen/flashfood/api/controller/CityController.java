package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.CityModelAseembler;
import br.com.allen.flashfood.api.assembler.CityRequestDisassembler;
import br.com.allen.flashfood.api.model.request.CityRequest;
import br.com.allen.flashfood.api.model.response.CityResponse;
import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.StateNotFoundException;
import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.repository.CityRepository;
import br.com.allen.flashfood.domain.service.CityRegistrationService;
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

    @Autowired
    private CityModelAseembler cityModelAseembler;

    @Autowired
    private CityRequestDisassembler cityRequestDisassembler;

    @GetMapping
    public List<CityResponse> getAllCity() {
        List<City> allCities = cityRepository.findAll();
        return cityModelAseembler.toCollectionModel(allCities);
    }

    @GetMapping("/{cityId}")
    public CityResponse getCityById(@PathVariable Long cityId) {
        City city = cityRegistration.findCityOrElseThrow(cityId);
        return cityModelAseembler.toModel(city);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityResponse addCity(@RequestBody @Valid CityRequest cityRequest) {
        try {
            City city = cityRequestDisassembler.toDomainObject(cityRequest);
            city = cityRegistration.saveCity(city);
            return cityModelAseembler.toModel(city);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cityId}")
    public CityResponse updateCity(@PathVariable Long cityId,
                                   @RequestBody @Valid CityRequest cityRequest) {
        try {
            City actualCity = cityRegistration.findCityOrElseThrow(cityId);
            cityRequestDisassembler.copyToDomainObject(cityRequest, actualCity);
            actualCity = cityRegistration.saveCity(actualCity);
            return cityModelAseembler.toModel(actualCity);
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
