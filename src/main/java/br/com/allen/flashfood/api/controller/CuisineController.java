package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.CuisineModelAssembler;
import br.com.allen.flashfood.api.assembler.CuisineRequestDisassembler;
import br.com.allen.flashfood.api.model.request.CuisineRequest;
import br.com.allen.flashfood.api.model.response.CuisineResponse;
import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.repository.CuisineRepository;
import br.com.allen.flashfood.domain.service.CuisineRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cuisines", produces = MediaType.APPLICATION_JSON_VALUE)
public class CuisineController {
    @Autowired
    private CuisineRepository cuisineRepository;

    @Autowired
    private CuisineRegistrationService cuisineRegistration;

    @Autowired
    private CuisineRequestDisassembler cuisineRequestDisassembler;

    @Autowired
    private CuisineModelAssembler cuisineModelAssembler;

    @GetMapping
    public List<CuisineResponse> getAllCuisine() {
        List<Cuisine> allCuisine = cuisineRepository.findAll();
        return cuisineModelAssembler.toCollectionModel(allCuisine);
    }

    @GetMapping("/{cuisineId}")
    public CuisineResponse getCuisineById(@PathVariable Long cuisineId) {
        Cuisine cuisine = cuisineRegistration.findCuisineOrElseThrow(cuisineId);
        return cuisineModelAssembler.toModel(cuisine);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuisineResponse addCuisine(@RequestBody @Valid CuisineRequest cuisineRequest) {
        Cuisine cuisine = cuisineRequestDisassembler.toDomainObject(cuisineRequest);
        cuisine = cuisineRegistration.saveCuisine(cuisine);
        return cuisineModelAssembler.toModel(cuisine);
    }

    @PutMapping("/{cuisineId}")
    public CuisineResponse updateCuisine(@PathVariable Long cuisineId,
                                         @RequestBody @Valid CuisineRequest cuisineRequest) {
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
