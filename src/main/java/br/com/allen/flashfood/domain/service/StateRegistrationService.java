package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.StateNotFoundException;
import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


@Service
public class StateRegistrationService {
    public static final String STATE_IN_USE = "State with %d code cannot be removed because it is in use.";
    @Autowired
    private StateRepository stateRepository;

    public State saveState(State state) {
        return stateRepository.save(state);
    }

    public void deleteState(Long stateId) {
        try {
            stateRepository.deleteById(stateId);
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
