package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.repository.CuisineRepository;
import br.com.allen.flashfood.domain.service.CuisineRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return cuisineRepository.getAllCuisine();
    }

    @GetMapping("/{cuisineId}")
    public ResponseEntity<Cuisine> getCuisineById(@PathVariable Long cuisineId) {
        Cuisine cuisine = cuisineRepository.getCuisineById(cuisineId);
        if (cuisine != null) {
            return ResponseEntity.ok(cuisine);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cuisine addCuisine(@RequestBody Cuisine cuisine) {
        return cuisineRegistration.saveCuisine(cuisine);
    }

    @PutMapping("/{cuisineId}")
    public ResponseEntity<Cuisine> updateCuisine(@PathVariable Long cuisineId,
                                                 @RequestBody Cuisine cuisine) {
        Cuisine actualCuisine = cuisineRepository.getCuisineById(cuisineId);
        if (actualCuisine != null) {
            BeanUtils.copyProperties(cuisine, actualCuisine, "id");
            cuisineRegistration.saveCuisine(cuisine);
            return ResponseEntity.ok(cuisine);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cuisineId}")
    public ResponseEntity<Cuisine> deleteCuisineById(@PathVariable Long cuisineId) {
        try {
            cuisineRegistration.deleteCuisine(cuisineId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundedException e) {
            return ResponseEntity.notFound().build();
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
