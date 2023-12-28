package br.com.allen.flashfood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRequest {
  @NotBlank private String name;

  @NotBlank @Email private String email;
}
