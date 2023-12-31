package br.com.allen.flashfood.domain.exception;

public abstract class EntityNotFoundedException extends BusinessException {
  private static final long serialVersionUID = 1L;

  public EntityNotFoundedException(String message) {
    super(message);
  }
}
