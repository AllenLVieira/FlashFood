package br.com.allen.flashfood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorsType {
  ENTITY_IN_USE("/entity-in-use", "Entity in use"),
  BUSINESS_ERROR("/business-error", "Business rule violation"),
  MESSAGE_NOT_READABLE("/message-not-readable", "Message not readable"),
  INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
  RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
  SYSTEM_ERROR("/system-error", "System error"),
  INVALID_DATA("/invalid-data", "Invalid data");

  private final String title;
  private final String uri;

  ErrorsType(String path, String title) {
    this.uri = "https://www.flashfood.com.br" + path;
    this.title = title;
  }
}
