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
    public ResponseEntity<State> getStateById(@PathVariable Long stateId) {
        Optional<State> state = stateRepository.findById(stateId);
        return state.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State addState(@RequestBody State state) {
        return stateRegistration.saveState(state);
    }

    @PutMapping("/{stateId}")
    public ResponseEntity<State> updateRestaurant(@PathVariable Long stateId,
                                                  @RequestBody State state) {
        State actualState = stateRepository.findById(stateId).orElse(null);
        if (actualState != null) {
            BeanUtils.copyProperties(state, actualState, "id");
            actualState = stateRegistration.saveState(actualState);
            return ResponseEntity.ok(actualState);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{stateId}")
    public ResponseEntity<?> deleteState(@PathVariable Long stateId) {
        try {
            stateRegistration.deleteState(stateId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundedException e) {
            return ResponseEntity.notFound().build();
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
}
