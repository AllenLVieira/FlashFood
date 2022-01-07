package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.Cuisine;

import java.util.List;

public interface CuisineRepository {
    List<Cuisine> getAllCuisine();

    Cuisine getCuisineById(Long id);

    Cuisine saveCuisine(Cuisine cuisine);

    void deleteCuisine(Long id);
}
