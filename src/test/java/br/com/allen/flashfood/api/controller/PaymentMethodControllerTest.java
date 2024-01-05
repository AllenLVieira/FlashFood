package br.com.allen.flashfood.api.controller;

import static org.mockito.Mockito.*;

import br.com.allen.flashfood.api.assembler.PaymentMethodModelAssembler;
import br.com.allen.flashfood.api.assembler.PaymentMethodRequestDisassembler;
import br.com.allen.flashfood.api.model.request.PaymentMethodRequest;
import br.com.allen.flashfood.api.model.response.PaymentMethodResponse;
import br.com.allen.flashfood.domain.model.PaymentMethod;
import br.com.allen.flashfood.domain.repository.PaymentMehodRepository;
import br.com.allen.flashfood.domain.service.PaymentMethodRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PaymentMethodController.class})
@ExtendWith(SpringExtension.class)
class PaymentMethodControllerTest {
  @MockBean private PaymentMehodRepository paymentMehodRepository;

  @Autowired private PaymentMethodController paymentMethodController;

  @MockBean private PaymentMethodModelAssembler paymentMethodModelAssembler;

  @MockBean private PaymentMethodRegistrationService paymentMethodRegistrationService;

  @MockBean private PaymentMethodRequestDisassembler paymentMethodRequestDisassembler;

  /** Method under test: {@link PaymentMethodController#addPaymentMethod(PaymentMethodRequest)} */
  @Test
  void testAddPaymentMethod() throws Exception {
    // Arrange
    when(paymentMehodRepository.findAll()).thenReturn(new ArrayList<>());
    when(paymentMethodModelAssembler.toCollectionModel(Mockito.<Collection<PaymentMethod>>any()))
        .thenReturn(new ArrayList<>());

    PaymentMethodRequest paymentMethodRequest = new PaymentMethodRequest();
    paymentMethodRequest.setDescription("The characteristics of someone or something");
    String content = (new ObjectMapper()).writeValueAsString(paymentMethodRequest);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/payment-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(paymentMethodController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  /** Method under test: {@link PaymentMethodController#deletePaymentMethodById(Long)} */
  @Test
  void testDeletePaymentMethodById() throws Exception {
    // Arrange
    doNothing().when(paymentMethodRegistrationService).deletePaymentMethod(Mockito.<Long>any());
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.delete("/payment-methods/{paymentMethodId}", 1L);

    // Act
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(paymentMethodController).build().perform(requestBuilder);

    // Assert
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  /** Method under test: {@link PaymentMethodController#getAllPaymentMethods()} */
  @Test
  void testGetAllPaymentMethods() throws Exception {
    // Arrange
    when(paymentMehodRepository.findAll()).thenReturn(new ArrayList<>());
    when(paymentMethodModelAssembler.toCollectionModel(Mockito.<Collection<PaymentMethod>>any()))
        .thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/payment-methods");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(paymentMethodController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /** Method under test: {@link PaymentMethodController#getPaymentMethodById(Long)} */
  @Test
  void testGetPaymentMethodById() throws Exception {
    // Arrange
    PaymentMethod paymentMethod = new PaymentMethod();
    paymentMethod.setDescription("The characteristics of someone or something");
    paymentMethod.setId(1L);
    when(paymentMethodRegistrationService.findPaymentMethodOrElseThrow(Mockito.<Long>any()))
        .thenReturn(paymentMethod);

    PaymentMethodResponse paymentMethodResponse = new PaymentMethodResponse();
    paymentMethodResponse.setDescription("The characteristics of someone or something");
    paymentMethodResponse.setId(1L);
    when(paymentMethodModelAssembler.toModel(Mockito.<PaymentMethod>any()))
        .thenReturn(paymentMethodResponse);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/payment-methods/{paymentMethodId}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(paymentMethodController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(
            MockMvcResultMatchers.content()
                .string(
                    "{\"id\":1,\"description\":\"The characteristics of someone or something\"}"));
  }

  /**
   * Method under test: {@link PaymentMethodController#updatePaymentMethod(Long,
   * PaymentMethodRequest)}
   */
  @Test
  void testUpdatePaymentMethod() throws Exception {
    // Arrange
    PaymentMethod paymentMethod = new PaymentMethod();
    paymentMethod.setDescription("The characteristics of someone or something");
    paymentMethod.setId(1L);

    PaymentMethod paymentMethod2 = new PaymentMethod();
    paymentMethod2.setDescription("The characteristics of someone or something");
    paymentMethod2.setId(1L);
    when(paymentMethodRegistrationService.findPaymentMethodOrElseThrow(Mockito.<Long>any()))
        .thenReturn(paymentMethod);
    when(paymentMethodRegistrationService.savePaymentMethod(Mockito.<PaymentMethod>any()))
        .thenReturn(paymentMethod2);

    PaymentMethodResponse paymentMethodResponse = new PaymentMethodResponse();
    paymentMethodResponse.setDescription("The characteristics of someone or something");
    paymentMethodResponse.setId(1L);
    when(paymentMethodModelAssembler.toModel(Mockito.<PaymentMethod>any()))
        .thenReturn(paymentMethodResponse);
    doNothing()
        .when(paymentMethodRequestDisassembler)
        .copyToDomainObject(Mockito.<PaymentMethodRequest>any(), Mockito.<PaymentMethod>any());

    PaymentMethodRequest paymentMethodRequest = new PaymentMethodRequest();
    paymentMethodRequest.setDescription("The characteristics of someone or something");
    String content = (new ObjectMapper()).writeValueAsString(paymentMethodRequest);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/payment-methods/{paymentMethodId}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(paymentMethodController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(
            MockMvcResultMatchers.content()
                .string(
                    "{\"id\":1,\"description\":\"The characteristics of someone or something\"}"));
  }
}
