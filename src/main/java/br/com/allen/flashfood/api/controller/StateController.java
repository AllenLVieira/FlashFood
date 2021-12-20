package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {
    @Autowired
    private StateRepository stateRepository;

    @GetMapping
    public List<State> getAllStates() {
        return stateRepository.getAllStates();
    }
}
