package br.com.allen.flashfood.domain.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.StateNotFoundException;
import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.StateRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StateRegistrationService.class})
@ExtendWith(SpringExtension.class)
class StateRegistrationServiceTest {
  State state;
  @Autowired private StateRegistrationService underTest;
  @MockBean private StateRepository stateRepository;

  @BeforeEach
  void setUp() {
    state = new State();
    state.setId(123L);
    state.setName("Name");
  }

  /** Method under test: {@link StateRegistrationService#saveState(State)} */
  @Test
  void shouldSaveState() {
    when(stateRepository.save(any())).thenReturn(state);

    State state1 = new State();
    state1.setId(123L);
    state1.setName("Name");
    assertSame(state, underTest.saveState(state1));
    verify(stateRepository).save(any());
  }

  /** Method under test: {@link StateRegistrationService#saveState(State)} */
  @Test
  void shouldThrowEmptyResultDataAccessExceptionWhenTryToSaveState() {
    when(stateRepository.save(any())).thenThrow(new EmptyResultDataAccessException(3));

    assertThrows(EmptyResultDataAccessException.class, () -> underTest.saveState(state));
    verify(stateRepository).save(any());
  }

  /** Method under test: {@link StateRegistrationService#deleteState(Long)} */
  @Test
  void shouldDeleteState() {
    doNothing().when(stateRepository).flush();
    doNothing().when(stateRepository).deleteById(any());
    underTest.deleteState(123L);
    verify(stateRepository).flush();
    verify(stateRepository).deleteById(any());
  }

  /** Method under test: {@link StateRegistrationService#deleteState(Long)} */
  @Test
  void shouldThrowsStateNotFoundExceptionWhenDeleteState() {
    doThrow(new EmptyResultDataAccessException(3)).when(stateRepository).deleteById(any());
    assertThrows(StateNotFoundException.class, () -> underTest.deleteState(123L));
    verify(stateRepository).deleteById(any());
  }

  /** Method under test: {@link StateRegistrationService#deleteState(Long)} */
  @Test
  void shouldThrowsEntityInUseExceptionWhenDeleteState() {
    doThrow(new DataIntegrityViolationException("An error occurred"))
        .when(stateRepository)
        .deleteById(any());
    assertThrows(EntityInUseException.class, () -> underTest.deleteState(123L));
    verify(stateRepository).deleteById(any());
  }

  /** Method under test: {@link StateRegistrationService#findStateOrElseThrow(Long)} */
  @Test
  void shouldFindStateById() {
    Optional<State> ofResult = Optional.of(state);
    when(stateRepository.findById(any())).thenReturn(ofResult);
    assertSame(state, underTest.findStateOrElseThrow(123L));
    verify(stateRepository).findById(any());
  }

  /** Method under test: {@link StateRegistrationService#findStateOrElseThrow(Long)} */
  @Test
  void shouldThrowsStateNotFoundExceptionWhenTryToFindState() {
    when(stateRepository.findById(any())).thenReturn(Optional.empty());
    assertThrows(StateNotFoundException.class, () -> underTest.findStateOrElseThrow(123L));
    verify(stateRepository).findById(any());
  }

  /** Method under test: {@link StateRegistrationService#findStateOrElseThrow(Long)} */
  @Test
  void shouldThrowsEmptyResultDataAccessExceptionWhenTryToFindState() {
    when(stateRepository.findById(any())).thenThrow(new EmptyResultDataAccessException(3));
    assertThrows(EmptyResultDataAccessException.class, () -> underTest.findStateOrElseThrow(123L));
    verify(stateRepository).findById(any());
  }
}
