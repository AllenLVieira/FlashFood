package br.com.allen.flashfood.core.jackson;

import br.com.allen.flashfood.api.model.mixin.CityMixin;
import br.com.allen.flashfood.api.model.mixin.CuisineMixin;
import br.com.allen.flashfood.api.model.mixin.RestaurantMixin;
import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.model.Restaurant;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
        setMixInAnnotation(City.class, CityMixin.class);
        setMixInAnnotation(Cuisine.class, CuisineMixin.class);
    }
}
