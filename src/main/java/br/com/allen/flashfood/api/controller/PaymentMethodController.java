package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.PaymentMethodModelAssembler;
import br.com.allen.flashfood.api.assembler.PaymentMethodRequestDisassembler;
import br.com.allen.flashfood.api.controller.openapi.PaymentMethodControllerOpenApi;
import br.com.allen.flashfood.api.model.request.PaymentMethodRequest;
import br.com.allen.flashfood.api.model.response.PaymentMethodResponse;
import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.PaymentMethodNotFoundException;
import br.com.allen.flashfood.domain.model.PaymentMethod;
import br.com.allen.flashfood.domain.repository.PaymentMehodRepository;
import br.com.allen.flashfood.domain.service.PaymentMethodRegistrationService;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentMethodController implements PaymentMethodControllerOpenApi {

  private final PaymentMehodRepository paymentMehodRepository;
  private final PaymentMethodRegistrationService paymentMethodRegistrationService;
  private final PaymentMethodModelAssembler paymentMethodModelAssembler;
  private final PaymentMethodRequestDisassembler paymentMethodRequestDisassembler;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<PaymentMethodResponse>> getAllPaymentMethods(
      ServletWebRequest request) {
    ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
    String eTag = "0";
    OffsetDateTime lastUpdateDate = paymentMehodRepository.getLastUpdatedOffsetDate();
    if (lastUpdateDate != null) {
      eTag = String.valueOf(lastUpdateDate.toEpochSecond());
    }

    if (request.checkNotModified(eTag)) {
      return null;
    }

    List<PaymentMethod> allPaymentMethods = paymentMehodRepository.findAll();
    List<PaymentMethodResponse> collectionModel =
        paymentMethodModelAssembler.toCollectionModel(allPaymentMethods);
    return ResponseEntity.ok()
        .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
        .eTag(eTag)
        .body(collectionModel);
  }

  @GetMapping(value = "/{paymentMethodId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PaymentMethodResponse> getPaymentMethodById(
      @PathVariable Long paymentMethodId, ServletWebRequest request) {
    ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
    String eTag = "0";
    OffsetDateTime lastModified =
        paymentMehodRepository.getLastUpdatedOffsetDateById(paymentMethodId);

    if (lastModified != null) {
      eTag = String.valueOf(lastModified.toEpochSecond());
    }

    if (request.checkNotModified(eTag)) {
      return null;
    }
    PaymentMethod paymentMethod =
        paymentMethodRegistrationService.findPaymentMethodOrElseThrow(paymentMethodId);
    PaymentMethodResponse model = paymentMethodModelAssembler.toModel(paymentMethod);
    return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(model);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public PaymentMethodResponse addPaymentMethod(
      @RequestBody @Valid PaymentMethodRequest paymentMethodRequest) {
    PaymentMethod paymentMethod =
        paymentMethodRequestDisassembler.toDomainObject(paymentMethodRequest);
    paymentMethod = paymentMethodRegistrationService.savePaymentMethod(paymentMethod);
    return paymentMethodModelAssembler.toModel(paymentMethod);
  }

  @PutMapping(value = "/{paymentMethodId}", produces = MediaType.APPLICATION_JSON_VALUE)
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
