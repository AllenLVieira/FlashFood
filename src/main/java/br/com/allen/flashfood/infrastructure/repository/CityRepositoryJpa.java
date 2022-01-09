package br.com.allen.flashfood.infrastructure.repository;

import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.repository.CityRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CityRepositoryJpa implements CityRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<City> getAllCities() {
        return entityManager.createQuery("from City", City.class)
                .getResultList();
    }

    @Override
    public City getCityById(Long id) {
        return entityManager.find(City.class, id);
    }

    @Transactional
    @Override
    public City saveCity(City city) {
        return entityManager.merge(city);
    }

    @Transactional
    @Override
    public void removeCity(Long cityId) {
        City city = getCityById(cityId);
        if (city == null) {
            throw new EmptyResultDataAccessException(1);
        }
        entityManager.remove(city);
    }
}
