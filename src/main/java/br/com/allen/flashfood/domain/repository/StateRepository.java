package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.State;

import java.util.List;

public interface StateRepository {
    List<State> getAllStates();

    State getStateById(Long id);

    State addState(State state);

    void removeState(State state);
}
