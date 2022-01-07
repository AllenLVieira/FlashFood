package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.City;

import java.util.List;

public interface CityRepository {
    List<City> getAllCities();

    City getCityById(Long id);

    City saveCity(City city);

    void removeCity(City city);
}
