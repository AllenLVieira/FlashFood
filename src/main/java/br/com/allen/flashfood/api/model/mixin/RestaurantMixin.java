package br.com.allen.flashfood.api.model.mixin;

import br.com.allen.flashfood.domain.model.Address;
import br.com.allen.flashfood.domain.model.Cuisine;
import br.com.allen.flashfood.domain.model.PaymentMethod;
import br.com.allen.flashfood.domain.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;

public abstract class RestaurantMixin {

    @JsonIgnore
    private List<Product> products;

    @JsonIgnore
    private List<PaymentMethod> paymentMethod;

    @JsonIgnore
    private Address address;

    @JsonIgnore
    private LocalDateTime registrationDate;

    @JsonIgnore
    private LocalDateTime updateDate;

    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private Cuisine cuisine;
}
