package br.com.allen.flashfood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {
  CREATED("Created"),
  CONFIRMED("Confirmed", CREATED),
  DELIVERED("Delivered", CONFIRMED),
  CANCELED("Canceled", CREATED);

  private final String description;

  private final List<OrderStatus> previousStatus;

  OrderStatus(String description, OrderStatus... previousStatus) {
    this.description = description;
    this.previousStatus = Arrays.asList(previousStatus);
  }

  public String getDescription() {
    return description;
  }

  public boolean cannotChangeStatusTo(OrderStatus newStatus) {
    return !newStatus.previousStatus.contains(this);
  }
}
