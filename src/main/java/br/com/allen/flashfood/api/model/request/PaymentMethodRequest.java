package br.com.allen.flashfood.api.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodRequest {

  @NotBlank private String description;
}
