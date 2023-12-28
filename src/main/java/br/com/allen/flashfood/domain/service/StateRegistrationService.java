package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.StateNotFoundException;
import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StateRegistrationService {
    public static final String STATE_IN_USE = "State with %d code cannot be removed because it is in use.";

    private final StateRepository stateRepository;

    @Transactional
    public State saveState(State state) {
        return stateRepository.save(state);
    }

    @Transactional
    public void deleteState(Long stateId) {
        try {
            stateRepository.deleteById(stateId);
            stateRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new StateNotFoundException(stateId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(STATE_IN_USE, stateId)
            );
        }
    }

    public State findStateOrElseThrow(Long stateId) {
        return stateRepository.findById(stateId)
                .orElseThrow(() -> new StateNotFoundException(stateId));
    }

}
