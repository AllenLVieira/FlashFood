package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.CuisineNotFoundException;
import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.repository.CuisineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CuisineRegistrationService.class})
@ExtendWith(SpringExtension.class)
class CuisineRegistrationServiceTest {
    @Autowired
    private CuisineRegistrationService underTest;

    @MockBean
    private CuisineRepository cuisineRepository;

    private Cuisine cuisine;

    @BeforeEach
    void setUp() {
        cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Cuisine");
        cuisine.setRestaurant(new ArrayList<>());
    }

    /**
     * Method under test: {@link CuisineRegistrationService#saveCuisine(Cuisine)}
     */
    @Test
    void testSaveCuisine() {
        when(cuisineRepository.save(any())).thenReturn(cuisine);

        Cuisine newCuisine = new Cuisine();
        newCuisine.setId(123L);
        newCuisine.setName("Cuisine");
        newCuisine.setRestaurant(new ArrayList<>());
        assertSame(cuisine, underTest.saveCuisine(newCuisine));
        verify(cuisineRepository).save(any());
    }

    /**
     * Method under test: {@link CuisineRegistrationService#saveCuisine(Cuisine)}
     */
    @Test
    void shouldThrowEmptyResultWhenSave() {
        when(cuisineRepository.save(any())).thenThrow(new EmptyResultDataAccessException(3));
        assertThrows(EmptyResultDataAccessException.class, () -> underTest.saveCuisine(cuisine));
        verify(cuisineRepository).save(any());
    }

    /**
     * Method under test: {@link CuisineRegistrationService#deleteCuisine(Long)}
     */
    @Test
    void shouldDeleteCuisine() {
        doNothing().when(cuisineRepository).flush();
        doNothing().when(cuisineRepository).deleteById(any());
        underTest.deleteCuisine(123L);
        verify(cuisineRepository).flush();
        verify(cuisineRepository).deleteById(any());
    }

    /**
     * Method under test: {@link CuisineRegistrationService#deleteCuisine(Long)}
     */
    @Test
    void shouldThrowEntityInUseExceptionWhenDeleteCuisine() {
        doNothing().when(cuisineRepository).flush();
        doThrow(new DataIntegrityViolationException("Exception")).when(cuisineRepository).deleteById(any());
        assertThrows(EntityInUseException.class, () -> underTest.deleteCuisine(123L));
        verify(cuisineRepository).deleteById(any());
    }

    /**
     * Method under test: {@link CuisineRegistrationService#deleteCuisine(Long)}
     */
    @Test
    void shouldThrowCuisineNotFoundExceptionWhenDeleteCuisine() {
        doNothing().when(cuisineRepository).flush();
        doThrow(new EmptyResultDataAccessException(3)).when(cuisineRepository).deleteById(any());
        assertThrows(CuisineNotFoundException.class, () -> underTest.deleteCuisine(123L));
        verify(cuisineRepository).deleteById(any());
    }

    /**
     * Method under test: {@link CuisineRegistrationService#findCuisineOrElseThrow(Long)}
     */
    @Test
    void shouldFindCuisineWithSuccess() {
        Optional<Cuisine> ofResult = Optional.of(cuisine);
        when(cuisineRepository.findById(any())).thenReturn(ofResult);
        assertSame(cuisine, underTest.findCuisineOrElseThrow(123L));
        verify(cuisineRepository).findById(any());
    }

    /**
     * Method under test: {@link CuisineRegistrationService#findCuisineOrElseThrow(Long)}
     */
    @Test
    void shouldThrowCuisineNotFoundExceptionWhenTryToFind() {
        when(cuisineRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(CuisineNotFoundException.class, () -> underTest.findCuisineOrElseThrow(123L));
        verify(cuisineRepository).findById(any());
    }

    /**
     * Method under test: {@link CuisineRegistrationService#findCuisineOrElseThrow(Long)}
     */
    @Test
    void shouldThrowEmptyResultExceptionWhenTryToFind() {
        when(cuisineRepository.findById(any())).thenThrow(new EmptyResultDataAccessException(3));
        assertThrows(EmptyResultDataAccessException.class, () -> underTest.findCuisineOrElseThrow(123L));
        verify(cuisineRepository).findById(any());
    }
}

