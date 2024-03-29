package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.CuisineNotFoundException;
import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.repository.CuisineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CuisineRegistrationService {
  public static final String CUISINE_IN_USE =
      "Cuisine with %d code cannot be removed because it is in use.";
  private final CuisineRepository cuisineRepository;

  @Transactional
  public Cuisine saveCuisine(Cuisine cuisine) {
    return cuisineRepository.save(cuisine);
  }

  @Transactional
  public void deleteCuisine(Long cuisineId) {
    try {
      cuisineRepository.deleteById(cuisineId);
      cuisineRepository.flush();
    } catch (EmptyResultDataAccessException e) {
      throw new CuisineNotFoundException(cuisineId);
    } catch (DataIntegrityViolationException e) {
      throw new EntityInUseException(String.format(CUISINE_IN_USE, cuisineId));
    }
  }

  public Cuisine findCuisineOrElseThrow(Long cuisineId) {
    return cuisineRepository
        .findById(cuisineId)
        .orElseThrow(() -> new CuisineNotFoundException(cuisineId));
  }
}
