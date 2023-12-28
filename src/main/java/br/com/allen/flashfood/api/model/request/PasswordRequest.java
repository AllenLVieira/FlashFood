package br.com.allen.flashfood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordRequest {
  @NotBlank private String actualPassword;

  @NotBlank private String newPassword;
}
