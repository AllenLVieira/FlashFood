package br.com.allen.flashfood.api.model.mixin;

import br.com.allen.flashfood.domain.model.Restaurant;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public abstract class CuisineMixin {

    @JsonIgnore
    private List<Restaurant> restaurant;
}
