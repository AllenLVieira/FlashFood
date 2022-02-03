package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.StateRepository;
import br.com.allen.flashfood.domain.service.StateRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/states")
public class StateController {
    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private StateRegistrationService stateRegistration;

    @GetMapping
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    @GetMapping("/{stateId}")
    public State getStateById(@PathVariable Long stateId) {
        return stateRegistration.findStateOrElseThrow(stateId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State addState(@RequestBody State state) {
        return stateRegistration.saveState(state);
    }

    @PutMapping("/{stateId}")
    public State updateRestaurant(@PathVariable Long stateId,
                                                  @RequestBody State state) {
        State actualState = stateRegistration.findStateOrElseThrow(stateId);
        BeanUtils.copyProperties(state, actualState, "id");
        return stateRegistration.saveState(actualState);
    }

    @DeleteMapping("/{stateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteState(@PathVariable Long stateId) {
        stateRegistration.deleteState(stateId);
    }
}
