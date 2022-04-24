package br.com.allen.flashfood.domain.exception;

public class UserNotFoundException extends EntityNotFoundedException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long userId) {
        this(String.format("There is no user register with code %d", userId));
    }
}
