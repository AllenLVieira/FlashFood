package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.repository.CuisineRepository;
import br.com.allen.flashfood.domain.service.CuisineRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuisines")
public class CuisineController {
    @Autowired
    private CuisineRepository cuisineRepository;

    @Autowired
    private CuisineRegistrationService cuisineRegistration;

    @GetMapping
    public List<Cuisine> getAllCuisine() {
        return cuisineRepository.findAll();
    }

    @GetMapping("/{cuisineId}")
    public Cuisine getCuisineById(@PathVariable Long cuisineId) {
        return cuisineRegistration.findCuisineOrElseThrow(cuisineId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cuisine addCuisine(@RequestBody Cuisine cuisine) {
        return cuisineRegistration.saveCuisine(cuisine);
    }

    @PutMapping("/{cuisineId}")
    public Cuisine updateCuisine(@PathVariable Long cuisineId,
                                                 @RequestBody Cuisine cuisine) {
        Cuisine actualCuisine = cuisineRegistration.findCuisineOrElseThrow(cuisineId);
        BeanUtils.copyProperties(cuisine, actualCuisine, "id");
        return cuisineRegistration.saveCuisine(actualCuisine);
    }

    @DeleteMapping("/{cuisineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCuisineById(@PathVariable Long cuisineId) {
        cuisineRegistration.deleteCuisine(cuisineId);
    }
}
