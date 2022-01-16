package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class StateRegistrationService {
    @Autowired
    private StateRepository stateRepository;

    public State saveState(State state) {
        return stateRepository.save(state);
    }

    public void deleteState(Long stateId) {
        try {
            stateRepository.deleteById(stateId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundedException(
                    String.format("There is no state registration with code %d", stateId)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("State with %d code cannot be removed because it is in use.", stateId)
            );
        }
    }
}
