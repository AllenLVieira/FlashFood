package br.com.allen.flashfood.domain.exception;

public class PaymentMethodNotFoundException extends EntityNotFoundedException {

  private static final long serialVersionUID = 1L;

  public PaymentMethodNotFoundException(String message) {
    super(message);
  }

  public PaymentMethodNotFoundException(Long paymentMethodId) {
    this(String.format("There is no payment form registration with code %d", paymentMethodId));
  }
}
