package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.model.request.PaymentMethodRequest;
import br.com.allen.flashfood.api.model.response.PaymentMethodResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Tag(
    name = "Payment Method",
    description =
        "Responsible for managing different payment methods in the FlashFood"
            + " application. This controller enables the addition, update, retrieval, and deletion of payment methods,"
            + " facilitating diverse payment options for users. It plays a vital role in ensuring a seamless and"
            + " flexible payment experience, catering to various customer preferences.")
public interface PaymentMethodControllerOpenApi {

  @Operation(description = "Get all the payment methods in the Flashfood application.")
  ResponseEntity<List<PaymentMethodResponse>> getAllPaymentMethods(
      ServletWebRequest request);


  @Operation(description = "Get a payment method by Id in the Flashfood application.")
  ResponseEntity<PaymentMethodResponse> getPaymentMethodById(
      Long paymentMethodId, ServletWebRequest request);
  
  @Operation(description = "Add a new payment method in the Flashfood application.")
  PaymentMethodResponse addPaymentMethod(
      PaymentMethodRequest paymentMethodRequest);
  
  @Operation(description = "Update a payment method by Id in the Flashfood application.")
  PaymentMethodResponse updatePaymentMethod(
      Long paymentMethodId,
      PaymentMethodRequest paymentMethodRequest);
  
  @Operation(description = "Delete a payment method by Id in the Flashfood application.")
  void deletePaymentMethodById(Long paymentMethodId);
}
