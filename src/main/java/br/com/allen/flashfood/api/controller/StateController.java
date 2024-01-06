package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.StateModelAssembler;
import br.com.allen.flashfood.api.assembler.StateRequestDisassembler;
import br.com.allen.flashfood.api.model.request.StateRequest;
import br.com.allen.flashfood.api.model.response.StateResponse;
import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.StateRepository;
import br.com.allen.flashfood.domain.service.StateRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/states", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(
    name = "State",
    description =
        "Manages all operations related to state entities in the FlashFood"
            + " application. This controller facilitates the creation of new state entries, updates to existing"
            + " state information, retrieval of state details, and deletion of states as needed. It plays a key role"
            + " in managing geographical data, essential for location-based functionalities and user preferences.")
public class StateController {
  private final StateRepository stateRepository;

  private final StateRegistrationService stateRegistration;

  private final StateModelAssembler stateModelAssembler;

  private final StateRequestDisassembler stateRequestDisassembler;

  @GetMapping
  @Operation(description = "Get all the states in the Flashfood application.")
  public List<StateResponse> getAllStates() {
    List<State> allStates = stateRepository.findAll();
    return stateModelAssembler.toCollectionModel(allStates);
  }

  @GetMapping("/{stateId}")
  @Operation(description = "Get a state by Id in the Flashfood application.")
  public StateResponse getStateById(@PathVariable Long stateId) {
    State state = stateRegistration.findStateOrElseThrow(stateId);
    return stateModelAssembler.toModel(state);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(description = "Add new state to the Flashfood application.")
  public StateResponse addState(@RequestBody @Valid StateRequest stateRequest) {
    State state = stateRequestDisassembler.toDomainObject(stateRequest);
    state = stateRegistration.saveState(state);
    return stateModelAssembler.toModel(state);
  }

  @PutMapping("/{stateId}")
  @Operation(description = "Update state by Id in the Flashfood application.")
  public StateResponse updateRestaurant(
      @PathVariable Long stateId, @RequestBody @Valid StateRequest stateRequest) {
    State actualState = stateRegistration.findStateOrElseThrow(stateId);
    stateRequestDisassembler.copyToDomainObject(stateRequest, actualState);
    actualState = stateRegistration.saveState(actualState);
    return stateModelAssembler.toModel(actualState);
  }

  @DeleteMapping("/{stateId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Remove state by id in the Flashfood application.")
  public void deleteState(@PathVariable Long stateId) {
    stateRegistration.deleteState(stateId);
  }
}
