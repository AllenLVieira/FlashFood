package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.model.request.StateRequest;
import br.com.allen.flashfood.api.model.response.StateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(
    name = "State",
    description =
        "Manages all operations related to state entities in the FlashFood"
            + " application. This controller facilitates the creation of new state entries, updates to existing"
            + " state information, retrieval of state details, and deletion of states as needed. It plays a key role"
            + " in managing geographical data, essential for location-based functionalities and user preferences.")
public interface StateControllerOpenApi {
  @Operation(description = "Get all the states in the Flashfood application.")
  List<StateResponse> getAllStates();

  @Operation(description = "Get a state by Id in the Flashfood application.")
  StateResponse getStateById(Long stateId);

  @Operation(description = "Add new state to the Flashfood application.")
  StateResponse addState(StateRequest stateRequest);
  
  @Operation(description = "Update state by Id in the Flashfood application.")
  StateResponse updateRestaurant(
      Long stateId, StateRequest stateRequest);
  
  @Operation(description = "Remove state by id in the Flashfood application.")
  void deleteState(Long stateId);
}
