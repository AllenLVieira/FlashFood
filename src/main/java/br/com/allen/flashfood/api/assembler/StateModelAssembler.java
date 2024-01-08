package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.controller.StateController;
import br.com.allen.flashfood.api.model.response.StateResponse;
import br.com.allen.flashfood.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class StateModelAssembler extends RepresentationModelAssemblerSupport<State, StateResponse> {
  private final ModelMapper modelMapper;

  public StateModelAssembler(ModelMapper modelMapper) {
    super(StateController.class, StateResponse.class);
    this.modelMapper = modelMapper;
  }

  @Override
  public StateResponse toModel(State state) {
    StateResponse response = createModelWithId(state.getId(), state);
    modelMapper.map(state, response);

    response.add(WebMvcLinkBuilder.linkTo(StateController.class).withRel("states"));
    return response;
  }

  @Override
  public CollectionModel<StateResponse> toCollectionModel(Iterable<? extends State> entities) {
    return super.toCollectionModel(entities)
        .add(WebMvcLinkBuilder.linkTo(StateController.class).withSelfRel());
  }
}
