package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.repository.CuisineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cuisines")
public class CuisineController {
    @Autowired
    private CuisineRepository cuisineRepository;

    @GetMapping
    public List<Cuisine> getAllCuisine() {
        return cuisineRepository.getAllCuisine();
    }

    @GetMapping("/{cuisineId}")
    public Cuisine getCuisineById(@PathVariable Long cuisineId) {
        return cuisineRepository.getCuisineById(cuisineId);
    }
}
