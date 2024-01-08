package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.CuisineModelAssembler;
import br.com.allen.flashfood.api.assembler.CuisineRequestDisassembler;
import br.com.allen.flashfood.api.controller.openapi.CuisineControllerOpenApi;
import br.com.allen.flashfood.api.model.request.CuisineRequest;
import br.com.allen.flashfood.api.model.response.CuisineResponse;
import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.repository.CuisineRepository;
import br.com.allen.flashfood.domain.service.CuisineRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cuisines", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CuisineController implements CuisineControllerOpenApi {
  private final CuisineRepository cuisineRepository;
  private final CuisineRegistrationService cuisineRegistration;
  private final CuisineRequestDisassembler cuisineRequestDisassembler;
  private final CuisineModelAssembler cuisineModelAssembler;
  private final PagedResourcesAssembler<Cuisine> pagedResourcesAssembler;

  /**
   * Returns a list of all registered cuisines.
   *
   * @param pageable can receive as query params "size", "page" and "sort"
   * @return a list of cuisines and information about pagination.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public PagedModel<CuisineResponse> getAllCuisine(Pageable pageable) {
    Page<Cuisine> cuisinePage = cuisineRepository.findAll(pageable);
    PagedModel<CuisineResponse> pagedResponse =
        pagedResourcesAssembler.toModel(cuisinePage, cuisineModelAssembler);

    return pagedResponse;
  }

  @GetMapping(value = "/{cuisineId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public CuisineResponse getCuisineById(@PathVariable Long cuisineId) {
    Cuisine cuisine = cuisineRegistration.findCuisineOrElseThrow(cuisineId);
    return cuisineModelAssembler.toModel(cuisine);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public CuisineResponse addCuisine(@RequestBody @Valid CuisineRequest cuisineRequest) {
    Cuisine cuisine = cuisineRequestDisassembler.toDomainObject(cuisineRequest);
    cuisine = cuisineRegistration.saveCuisine(cuisine);
    return cuisineModelAssembler.toModel(cuisine);
  }

  @PutMapping(value = "/{cuisineId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public CuisineResponse updateCuisine(
      @PathVariable Long cuisineId, @RequestBody @Valid CuisineRequest cuisineRequest) {
    Cuisine actualCuisine = cuisineRegistration.findCuisineOrElseThrow(cuisineId);
    cuisineRequestDisassembler.copyToDomainObject(cuisineRequest, actualCuisine);
    actualCuisine = cuisineRegistration.saveCuisine(actualCuisine);
    return cuisineModelAssembler.toModel(actualCuisine);
  }

  @DeleteMapping("/{cuisineId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCuisineById(@PathVariable Long cuisineId) {
    cuisineRegistration.deleteCuisine(cuisineId);
  }
}
