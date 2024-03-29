package br.com.allen.flashfood.infrastructure.service.email;

import java.io.Serial;

public class EmailException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  public EmailException(String message) {
    super(message);
  }

  public EmailException(String message, Throwable cause) {
    super(message, cause);
  }
}
