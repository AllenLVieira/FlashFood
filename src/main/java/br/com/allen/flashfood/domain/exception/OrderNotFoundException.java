package br.com.allen.flashfood.domain.exception;

public class OrderNotFoundException extends EntityNotFoundedException {

    private static final long serialVersionUID = -620524270231625288L;

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(Long orderId) {
        this(String.format("There is no order record with code %d",
                orderId));
    }
}
