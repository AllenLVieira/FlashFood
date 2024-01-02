package br.com.allen.flashfood.domain.model;

import br.com.allen.flashfood.domain.event.CancelledOrderEvent;
import br.com.allen.flashfood.domain.event.ConfirmedOrderEvent;
import br.com.allen.flashfood.domain.exception.BusinessException;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class DeliveryOrder extends AbstractAggregateRoot<DeliveryOrder> {
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String orderCode;

  private BigDecimal subtotal;

  private BigDecimal freightRate;

  private BigDecimal amount;

  @Embedded private Address deliveryAddress;

  @Enumerated(EnumType.STRING)
  private OrderStatus status = OrderStatus.CREATED;

  @CreationTimestamp private OffsetDateTime registrationDate;

  private OffsetDateTime confirmationDate;

  private OffsetDateTime cancellationDate;

  private OffsetDateTime deliveryDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private PaymentMethod paymentMethod;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Restaurant restaurant;

  @ManyToOne
  @JoinColumn(name = "user_client_id", nullable = false)
  private User user;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> items = new ArrayList<>();

  public void calculateTotalAmount() {
    getItems().forEach(OrderItem::calculateTotalPrice);

    this.subtotal =
        getItems().stream()
            .map(item -> item.getTotalPrice())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    this.amount = this.subtotal.add(this.freightRate);
  }

  public void confirm() {
    setStatus(OrderStatus.CONFIRMED);
    setConfirmationDate(OffsetDateTime.now());

    registerEvent(new ConfirmedOrderEvent(this));
  }

  public void deliver() {
    setStatus(OrderStatus.DELIVERED);
    setDeliveryDate(OffsetDateTime.now());
  }

  public void cancel() {
    setStatus(OrderStatus.CANCELED);
    setCancellationDate(OffsetDateTime.now());

    registerEvent(new CancelledOrderEvent(this));
  }

  public void setStatus(OrderStatus newStatus) {
    if (getStatus().cannotChangeStatusTo(newStatus)) {
      throw new BusinessException(
          String.format(
              "Order status %s cannot be changed from %s to %s.",
              getOrderCode(), getStatus().getDescription(), newStatus.getDescription()));
    }
    this.status = newStatus;
  }

  @PrePersist
  private void generateNewOrderCode() {
    setOrderCode(UUID.randomUUID().toString());
  }
}
