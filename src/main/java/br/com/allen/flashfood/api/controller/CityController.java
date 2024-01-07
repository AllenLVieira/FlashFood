package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.ResourceUriUtils;
import br.com.allen.flashfood.api.assembler.CityModelAssembler;
import br.com.allen.flashfood.api.assembler.CityRequestDisassembler;
import br.com.allen.flashfood.api.controller.openapi.CityControllerOpenApi;
import br.com.allen.flashfood.api.model.request.CityRequest;
import br.com.allen.flashfood.api.model.response.CityResponse;
import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.StateNotFoundException;
import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.repository.CityRepository;
import br.com.allen.flashfood.domain.service.CityRegistrationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CityController implements CityControllerOpenApi {

  private final CityRepository cityRepository;
  private final CityRegistrationService cityRegistration;
  private final CityModelAssembler cityModelAssembler;
  private final CityRequestDisassembler cityRequestDisassembler;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public CollectionModel<CityResponse> getAllCity() {
    List<City> allCities = cityRepository.findAll();
    List<CityResponse> citiesResponse = cityModelAssembler.toCollectionModel(allCities);
    citiesResponse.forEach(
        response ->  {
          response.add(
              WebMvcLinkBuilder.linkTo(
                      WebMvcLinkBuilder.methodOn(CityController.class)
                          .getCityById(response.getId()))
                  .withSelfRel());
          response.add(
              WebMvcLinkBuilder.linkTo(
                      WebMvcLinkBuilder.methodOn(CityController.class).getAllCity())
                  .withRel("cities"));
          response
              .getState()
              .add(
                  WebMvcLinkBuilder.linkTo(
                          WebMvcLinkBuilder.methodOn(StateController.class)
                              .getStateById(response.getState().getId()))
                      .withSelfRel());
        });

    CollectionModel<CityResponse> cityResponses = CollectionModel.of(citiesResponse);
    cityResponses.add(WebMvcLinkBuilder.linkTo(CityController.class).withSelfRel());

    return cityResponses;
  }

  @GetMapping(value = "/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public CityResponse getCityById(@PathVariable Long cityId) {
    City city = cityRegistration.findCityOrElseThrow(cityId);
    CityResponse response = cityModelAssembler.toModel(city);

    response.add(
        WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CityController.class).getCityById(response.getId()))
            .withSelfRel());
    response.add(
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CityController.class).getAllCity())
            .withRel("cities"));
    response
        .getState()
        .add(
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(StateController.class)
                        .getStateById(response.getState().getId()))
                .withSelfRel());

    return response;
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public CityResponse addCity(@RequestBody @Valid CityRequest cityRequest) {
    try {
      City city = cityRequestDisassembler.toDomainObject(cityRequest);
      city = cityRegistration.saveCity(city);
      CityResponse response = cityModelAssembler.toModel(city);
      ResourceUriUtils.addUriToResponseHeader(response.getId());
      return response;
    } catch (StateNotFoundException e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }

  @PutMapping(value = "/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
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
  public void deleteCity(@PathVariable Long cityId) {
    cityRegistration.deleteCity(cityId);
  }
}
