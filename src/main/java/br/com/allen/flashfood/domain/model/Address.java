package br.com.allen.flashfood.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Embeddable
public class Address {

  @Column(name = "address_zipcode")
  private String zipCode;

  @Column(name = "address_street")
  private String street;

  @Column(name = "address_number")
  private String number;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "address_city_id")
  private City city;

  @Column(name = "address_district")
  private String district;

  @Column(name = "address_complement")
  private String complement;
}
