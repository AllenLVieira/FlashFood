package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.request.StateRequest;
import br.com.allen.flashfood.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateRequestDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public State toDomainObject(StateRequest stateRequest) {
        return modelMapper.map(stateRequest, State.class);
    }

    public void copyToDomainObject(StateRequest stateRequest, State state) {
        modelMapper.map(stateRequest, state);
    }
}
