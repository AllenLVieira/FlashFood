package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.response.StateResponse;
import br.com.allen.flashfood.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StateModelAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public StateResponse toModel(State state) {
        return modelMapper.map(state, StateResponse.class);
    }

    public List<StateResponse> toCollectionModel(List<State> states) {
        return states.stream()
                .map(state -> toModel(state))
                .collect(Collectors.toList());
    }
}
