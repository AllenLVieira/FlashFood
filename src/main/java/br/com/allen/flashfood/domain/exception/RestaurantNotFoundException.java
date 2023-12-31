package br.com.allen.flashfood.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundedException {
  private static final long serialVersionUID = 1L;

  public RestaurantNotFoundException(String message) {
    super(message);
  }

  public RestaurantNotFoundException(Long restaurantId) {
    this(String.format("There is no restaurant register with code %d", restaurantId));
  }
}
