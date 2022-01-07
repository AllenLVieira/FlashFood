package br.com.allen.flashfood.domain.exception;

public class EntityNotFoundedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EntityNotFoundedException(String message) {
        super(message);
    }
}
