package br.com.allen.flashfood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

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
}