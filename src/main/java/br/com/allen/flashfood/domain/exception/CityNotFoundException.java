package br.com.allen.flashfood.domain.exception;

public class CityNotFoundException extends EntityNotFoundedException {
    private static final long serialVersionUID = 1L;

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(Long cityId) {
        this(String.format("There is no city register with code %d", cityId));
    }
}
