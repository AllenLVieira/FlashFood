package br.com.allen.flashfood.domain.exception;

public class OrderNotFoundException extends EntityNotFoundedException {

  private static final long serialVersionUID = -620524270231625288L;

  public OrderNotFoundException(String orderCode) {
    super(String.format("There is no order record with code %s", orderCode));
  }
}
