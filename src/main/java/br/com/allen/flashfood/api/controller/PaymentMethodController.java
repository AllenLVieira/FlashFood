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
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentMethodController {

  @Autowired private PaymentMehodRepository paymentMehodRepository;

  @Autowired private PaymentMethodRegistrationService paymentMethodRegistrationService;

  @Autowired private PaymentMethodModelAssembler paymentMethodModelAssembler;

  @Autowired private PaymentMethodRequestDisassembler paymentMethodRequestDisassembler;

  @GetMapping
  public ResponseEntity<List<PaymentMethodResponse>> getAllPaymentMethods() {
    List<PaymentMethod> allPaymentMethods = paymentMehodRepository.findAll();
    List<PaymentMethodResponse> collectionModel =
        paymentMethodModelAssembler.toCollectionModel(allPaymentMethods);
    return ResponseEntity.ok()
        .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
        .body(collectionModel);
  }

  @GetMapping("/{paymentMethodId}")
  public ResponseEntity<PaymentMethodResponse> getPaymentMethodById(
      @PathVariable Long paymentMethodId) {
    PaymentMethod paymentMethod =
        paymentMethodRegistrationService.findPaymentMethodOrElseThrow(paymentMethodId);
    PaymentMethodResponse model = paymentMethodModelAssembler.toModel(paymentMethod);
    return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(model);
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
