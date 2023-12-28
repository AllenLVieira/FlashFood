package br.com.allen.flashfood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PaymentMethodIdRequest {

  @NotNull private Long id;
}
