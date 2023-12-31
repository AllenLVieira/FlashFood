package br.com.allen.flashfood.api.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FamilyRequest {

  @NotBlank private String name;
}
