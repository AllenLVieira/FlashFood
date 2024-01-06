package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.CityModelAssembler;
import br.com.allen.flashfood.api.assembler.CityRequestDisassembler;
import br.com.allen.flashfood.api.model.request.CityRequest;
import br.com.allen.flashfood.api.model.response.CityResponse;
import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.StateNotFoundException;
import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.repository.CityRepository;
import br.com.allen.flashfood.domain.service.CityRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(
    name = "City",
    description =
        "Operations related to cities in the FlashFood application. This controller handles the management of city"
            + " data, including the creation of new city entries, retrieval of city details, updating existing"
            + " city data, and deletion of cities. It's integral for managing geographical and locational"
            + " information within the application.")
public class CityController {

  private final CityRepository cityRepository;
  private final CityRegistrationService cityRegistration;
  private final CityModelAssembler cityModelAssembler;
  private final CityRequestDisassembler cityRequestDisassembler;

  @GetMapping
  @Operation(description = "Get all the cities in the Flashfood application.")
  public List<CityResponse> getAllCity() {
    List<City> allCities = cityRepository.findAll();
    return cityModelAssembler.toCollectionModel(allCities);
  }

  @GetMapping("/{cityId}")
  @Operation(description = "Get a city by Id in the Flashfood application.")
  public CityResponse getCityById(@PathVariable Long cityId) {
    City city = cityRegistration.findCityOrElseThrow(cityId);
    return cityModelAssembler.toModel(city);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(description = "Add a new City to Flashfood application.")
  public CityResponse addCity(@RequestBody @Valid CityRequest cityRequest) {
    try {
      City city = cityRequestDisassembler.toDomainObject(cityRequest);
      city = cityRegistration.saveCity(city);
      return cityModelAssembler.toModel(city);
    } catch (StateNotFoundException e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }

  @PutMapping("/{cityId}")
  @Operation(description = "Update a city based on an Id in the Flashfood application.")
  public CityResponse updateCity(
      @PathVariable Long cityId, @RequestBody @Valid CityRequest cityRequest) {
    try {
      City actualCity = cityRegistration.findCityOrElseThrow(cityId);
      cityRequestDisassembler.copyToDomainObject(cityRequest, actualCity);
      actualCity = cityRegistration.saveCity(actualCity);
      return cityModelAssembler.toModel(actualCity);
    } catch (StateNotFoundException e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }

  @DeleteMapping("/{cityId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Delete a specific city by an Id in the Flashfood application.")
  public void deleteCity(@PathVariable Long cityId) {
    cityRegistration.deleteCity(cityId);
  }
}
