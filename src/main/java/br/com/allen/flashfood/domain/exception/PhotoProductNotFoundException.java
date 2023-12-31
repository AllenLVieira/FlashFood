package br.com.allen.flashfood.domain.exception;

import java.io.Serial;

public class PhotoProductNotFoundException extends EntityNotFoundedException {
  @Serial private static final long serialVersionUID = 1L;

  public PhotoProductNotFoundException(String message) {
    super(message);
  }

  public PhotoProductNotFoundException(Long restaurantId, Long productId) {
    this(
        String.format(
            "There is no photo registration for the product with code %d for the restaurant with code %d",
            productId, restaurantId));
  }
}
