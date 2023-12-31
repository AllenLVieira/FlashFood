package br.com.allen.flashfood.api.model.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private Boolean active;
}
