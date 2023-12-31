package br.com.allen.flashfood.api.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

import br.com.allen.flashfood.domain.service.OrderFlowService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {OrderFlowController.class})
@ExtendWith(SpringExtension.class)
class OrderFlowControllerTest {
  @Autowired private OrderFlowController underTest;

  @MockBean private OrderFlowService orderFlowService;

  /** Method under test: {@link OrderFlowController#confirmOrder(String)} */
  @Test
  void testConfirmOrder() throws Exception {
    doNothing().when(orderFlowService).confirmOrder(anyString());
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/orders/{orderCode}/confirmation", "123-456");
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(underTest).build().perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  /** Method under test: {@link OrderFlowController#deliverOrder(String)} */
  @Test
  void testDeliverOrder() throws Exception {
    doNothing().when(orderFlowService).deliverOrder(anyString());
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/orders/{orderCode}/delivered", "123-456");
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(underTest).build().perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  /** Method under test: {@link OrderFlowController#cancelOrder(String)} */
  @Test
  void testCancelOrder() throws Exception {
    doNothing().when(orderFlowService).cancelOrder(anyString());
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/orders/{orderCode}/cancellation", "123-456");
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(underTest).build().perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
  }
}
