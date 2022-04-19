package br.com.allen.flashfood.api.model.mixin;

import br.com.allen.flashfood.domain.model.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class CityMixin {
    
    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private State state;
}
