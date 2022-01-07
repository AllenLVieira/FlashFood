package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.repository.CuisineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CuisineRegistrationService {
    @Autowired
    private CuisineRepository cuisineRepository;

    public Cuisine saveCuisine(Cuisine cuisine) {
        return cuisineRepository.saveCuisine(cuisine);
    }

    public void deleteCuisine(Long cuisineId) {
        try {
            cuisineRepository.deleteCuisine(cuisineId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundedException(
                    String.format("There is no cuisine register with code %d", cuisineId)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("Cuisine with %d code cannot be removed because it is in use.", cuisineId)
            );
        }
    }
}
