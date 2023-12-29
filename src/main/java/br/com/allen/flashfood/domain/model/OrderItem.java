package br.com.allen.flashfood.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItem {
  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private BigDecimal unitPrice;

  private BigDecimal totalPrice;

  private Integer quantity;

  private String note;

  @ManyToOne
  @JoinColumn(nullable = false)
  private DeliveryOrder order;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Product product;

  public void calculateTotalPrice() {
    BigDecimal unitPriceInside = this.getUnitPrice();
    Integer quantityInside = this.getQuantity();

    if (unitPriceInside == null) {
      unitPriceInside = BigDecimal.ZERO;
    }

    if (quantityInside == null) {
      quantityInside = 0;
    }

    this.setTotalPrice(unitPriceInside.multiply(new BigDecimal(quantityInside)));
  }
}
