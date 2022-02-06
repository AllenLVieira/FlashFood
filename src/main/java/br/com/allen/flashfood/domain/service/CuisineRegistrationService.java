package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.CuisineNotFoundException;
import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.repository.CuisineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CuisineRegistrationService {
    public static final String CUISINE_IN_USE = "Cuisine with %d code cannot be removed because it is in use.";
    @Autowired
    private CuisineRepository cuisineRepository;

    public Cuisine saveCuisine(Cuisine cuisine) {
        return cuisineRepository.save(cuisine);
    }

    public void deleteCuisine(Long cuisineId) {
        try {
            cuisineRepository.deleteById(cuisineId);
        } catch (EmptyResultDataAccessException e) {
            throw new CuisineNotFoundException(cuisineId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(CUISINE_IN_USE, cuisineId)
            );
        }
    }

    public Cuisine findCuisineOrElseThrow(Long cuisineId){
        return cuisineRepository.findById(cuisineId)
                .orElseThrow(() -> new CuisineNotFoundException(cuisineId));
    }
}
