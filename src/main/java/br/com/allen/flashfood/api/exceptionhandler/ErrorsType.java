package br.com.allen.flashfood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorsType {
    ENTITY_NOT_FOUND("/entity-not-found", "Entity not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_ERROR("/business-error", "Business rule violation");
    private final String title;
    private final String uri;

    ErrorsType(String path, String title) {
        this.uri = "https://www.flashfood.com.br" + path;
        this.title = title;
    }

}
