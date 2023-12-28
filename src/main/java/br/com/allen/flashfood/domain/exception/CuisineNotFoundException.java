package br.com.allen.flashfood.domain.exception;

public class CuisineNotFoundException extends EntityNotFoundedException {
  private static final long serialVersionUID = 1L;

  public CuisineNotFoundException(String message) {
    super(message);
  }

  public CuisineNotFoundException(Long cuisineId) {
    this(String.format("There is no cuisine register with code %d", cuisineId));
  }
}
