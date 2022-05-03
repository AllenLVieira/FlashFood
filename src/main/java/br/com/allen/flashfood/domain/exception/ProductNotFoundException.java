package br.com.allen.flashfood.domain.exception;

public class ProductNotFoundException extends EntityNotFoundedException {

    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long restaurantId, Long productId) {
        this(String.format("There is no product registration with code %d for the restaurant code %d",
                productId, restaurantId));
    }
}
