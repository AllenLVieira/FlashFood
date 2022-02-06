package br.com.allen.flashfood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class EntityNotFoundedException extends BusinessException {
    private static final long serialVersionUID = 1L;

    public EntityNotFoundedException(String message) {
        super(message);
    }
}
