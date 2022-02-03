package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import static br.com.allen.flashfood.domain.service.CuisineRegistrationService.CUISINE_NOT_FOUND;

@Service
public class StateRegistrationService {
    public static final String STATE_NOT_FOUND = "There is no state register with code %d";
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
            throw new EntityNotFoundedException(
                    String.format(STATE_NOT_FOUND, stateId)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(STATE_IN_USE, stateId)
            );
        }
    }

    public State findStateOrElseThrow(Long stateId){
        return stateRepository.findById(stateId)
                .orElseThrow(() -> new EntityNotFoundedException(
                        String.format(CUISINE_NOT_FOUND, stateId)
                ));
    }

}
