package br.com.allen.flashfood.api.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {
  private String zipCode;
  private String street;
  private String number;
  private String district;
  private String complement;
  private CitySummaryResponse city;
}
