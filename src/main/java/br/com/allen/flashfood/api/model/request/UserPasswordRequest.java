package br.com.allen.flashfood.api.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordRequest extends UserRequest {
  @NotBlank private String password;
}
