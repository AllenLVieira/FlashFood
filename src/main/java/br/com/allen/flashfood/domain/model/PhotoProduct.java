package br.com.allen.flashfood.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "photo_product")
public class PhotoProduct {
  @EqualsAndHashCode.Include
  @Id
  @Column(name = "product_id")
  private long id;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  private Product product;

  private String filename;
  private String description;
  private String contentType;
  private Long filesize;

  public Long getRestaurantId() {
    if (getProduct() != null) {
      return getProduct().getRestaurant().getId();
    }
    return null;
  }
}
