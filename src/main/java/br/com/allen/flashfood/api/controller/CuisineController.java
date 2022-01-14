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
import java.util.Optional;

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
    public ResponseEntity<Cuisine> getCuisineById(@PathVariable Long cuisineId) {
        Optional<Cuisine> cuisine = cuisineRepository.findById(cuisineId);
        return cuisine.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cuisine addCuisine(@RequestBody Cuisine cuisine) {
        return cuisineRegistration.saveCuisine(cuisine);
    }

    @PutMapping("/{cuisineId}")
    public ResponseEntity<Cuisine> updateCuisine(@PathVariable Long cuisineId,
                                                 @RequestBody Cuisine cuisine) {
        Optional<Cuisine> actualCuisine = cuisineRepository.findById(cuisineId);
        if (actualCuisine.isPresent()) {
            BeanUtils.copyProperties(cuisine, actualCuisine.get(), "id");
            Cuisine saveCuisine = cuisineRegistration.saveCuisine(actualCuisine.get());
            return ResponseEntity.ok(saveCuisine);
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
