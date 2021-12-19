package br.com.allen.flashfood.infrastructure.repository;

import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.repository.CuisineRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CuisineRepositoryJpa implements CuisineRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cuisine> getAllCuisine() {
        return entityManager.createQuery("from Cuisine", Cuisine.class)
                .getResultList();
    }

    @Override
    public Cuisine getCuisineById(Long id) {
        return entityManager.find(Cuisine.class, id);
    }

    @Transactional
    @Override
    public Cuisine addCuisine(Cuisine cuisine) {
        return entityManager.merge(cuisine);
    }

    @Transactional
    @Override
    public void deleteCuisine(Cuisine cuisine) {
        cuisine = getCuisineById(cuisine.getId());
        entityManager.remove(cuisine);
    }
}
