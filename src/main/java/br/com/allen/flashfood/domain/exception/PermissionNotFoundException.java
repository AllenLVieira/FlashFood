package br.com.allen.flashfood.domain.exception;

public class PermissionNotFoundException extends EntityNotFoundedException {
    private static final long serialVersionUID = 1L;

    public PermissionNotFoundException(String message) {
        super(message);
    }

    public PermissionNotFoundException(Long permissionId) {
        this(String.format("There is no permission record with code %d",
                permissionId));
    }
}
