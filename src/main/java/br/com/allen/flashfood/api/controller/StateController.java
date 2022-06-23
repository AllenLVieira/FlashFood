package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.StateModelAssembler;
import br.com.allen.flashfood.api.assembler.StateRequestDisassembler;
import br.com.allen.flashfood.api.model.request.StateRequest;
import br.com.allen.flashfood.api.model.response.StateResponse;
import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.StateRepository;
import br.com.allen.flashfood.domain.service.StateRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController {
    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private StateRegistrationService stateRegistration;

    @Autowired
    private StateModelAssembler stateModelAssembler;

    @Autowired
    private StateRequestDisassembler stateRequestDisassembler;

    @GetMapping
    public List<StateResponse> getAllStates() {
        List<State> allStates = stateRepository.findAll();
        return stateModelAssembler.toCollectionModel(allStates);
    }

    @GetMapping("/{stateId}")
    public StateResponse getStateById(@PathVariable Long stateId) {
        State state = stateRegistration.findStateOrElseThrow(stateId);
        return stateModelAssembler.toModel(state);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StateResponse addState(@RequestBody @Valid StateRequest stateRequest) {
        State state = stateRequestDisassembler.toDomainObject(stateRequest);
        state = stateRegistration.saveState(state);
        return stateModelAssembler.toModel(state);
    }

    @PutMapping("/{stateId}")
    public StateResponse updateRestaurant(@PathVariable Long stateId,
                                          @RequestBody @Valid StateRequest stateRequest) {
        State actualState = stateRegistration.findStateOrElseThrow(stateId);
        stateRequestDisassembler.copyToDomainObject(stateRequest, actualState);
        actualState = stateRegistration.saveState(actualState);
        return stateModelAssembler.toModel(actualState);
    }

    @DeleteMapping("/{stateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteState(@PathVariable Long stateId) {
        stateRegistration.deleteState(stateId);
    }
}
