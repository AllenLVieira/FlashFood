package br.com.allen.flashfood.domain.exception;

public class FamilyNotFoundException extends EntityNotFoundedException {

    private static final long serialVersionUID = 1L;

    public FamilyNotFoundException(String message) {
        super(message);
    }

    public FamilyNotFoundException(Long groupId) {
        this(String.format("There is no group register with code %d", groupId));
    }
}
