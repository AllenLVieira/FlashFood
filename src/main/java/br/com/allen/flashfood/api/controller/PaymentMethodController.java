package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.PaymentMethodModelAssembler;
import br.com.allen.flashfood.api.assembler.PaymentMethodRequestDisassembler;
import br.com.allen.flashfood.api.model.request.PaymentMethodRequest;
import br.com.allen.flashfood.api.model.response.PaymentMethodResponse;
import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.PaymentMethodNotFoundException;
import br.com.allen.flashfood.domain.model.PaymentMethod;
import br.com.allen.flashfood.domain.repository.PaymentMehodRepository;
import br.com.allen.flashfood.domain.service.PaymentMethodRegistrationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentMethodController {

  private final PaymentMehodRepository paymentMehodRepository;
  private final PaymentMethodRegistrationService paymentMethodRegistrationService;
  private final PaymentMethodModelAssembler paymentMethodModelAssembler;
  private final PaymentMethodRequestDisassembler paymentMethodRequestDisassembler;

  @GetMapping
  public List<PaymentMethodResponse> getAllPaymentMethods() {
    List<PaymentMethod> allPaymentMethods = paymentMehodRepository.findAll();
    return paymentMethodModelAssembler.toCollectionModel(allPaymentMethods);
  }

  @GetMapping("/{paymentMethodId}")
  public PaymentMethodResponse getPaymentMethodById(@PathVariable Long paymentMethodId) {
    PaymentMethod paymentMethod =
        paymentMethodRegistrationService.findPaymentMethodOrElseThrow(paymentMethodId);
    return paymentMethodModelAssembler.toModel(paymentMethod);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PaymentMethodResponse addPaymentMethod(
      @RequestBody @Valid PaymentMethodRequest paymentMethodRequest) {
    PaymentMethod paymentMethod =
        paymentMethodRequestDisassembler.toDomainObject(paymentMethodRequest);
    paymentMethod = paymentMethodRegistrationService.savePaymentMethod(paymentMethod);
    return paymentMethodModelAssembler.toModel(paymentMethod);
  }

  @PutMapping("/{paymentMethodId}")
  public PaymentMethodResponse updatePaymentMethod(
      @PathVariable Long paymentMethodId,
      @RequestBody @Valid PaymentMethodRequest paymentMethodRequest) {
    try {
      PaymentMethod actualPaymentMethod =
          paymentMethodRegistrationService.findPaymentMethodOrElseThrow(paymentMethodId);
      paymentMethodRequestDisassembler.copyToDomainObject(
          paymentMethodRequest, actualPaymentMethod);
      return paymentMethodModelAssembler.toModel(
          paymentMethodRegistrationService.savePaymentMethod(actualPaymentMethod));
    } catch (PaymentMethodNotFoundException e) {
      throw new BusinessException(e.getMessage());
    }
  }

  @DeleteMapping("/{paymentMethodId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePaymentMethodById(@PathVariable Long paymentMethodId) {
    paymentMethodRegistrationService.deletePaymentMethod(paymentMethodId);
  }
}
