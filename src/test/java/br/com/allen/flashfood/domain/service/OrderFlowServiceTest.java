package br.com.allen.flashfood.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.allen.flashfood.domain.model.DeliveryOrder;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.model.User;
import br.com.allen.flashfood.domain.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderFlowServiceTest {

  @Mock private OrderRegistrationService orderService;

  @Mock private OrderRepository orderRepository;

  @Mock private EmailSenderService emailService;

  @InjectMocks private OrderFlowService orderFlowService;

  @Test
  void testConfirmOrder() {
    // Arrange
    String orderCode = "orderCode123";
    DeliveryOrder mockOrder = mock(DeliveryOrder.class);
    Restaurant mockRestaurant = mock(Restaurant.class);
    User mockUser = mock(User.class);

    when(orderService.findOrderOrElseThrow(orderCode)).thenReturn(mockOrder);

    // Act
    orderFlowService.confirmOrder(orderCode);

    // Assert
    verify(orderService).findOrderOrElseThrow(orderCode);
    verify(mockOrder).confirm();
  }

  @Test
  void testDeliverOrder() {
    // Arrange
    String orderCode = "orderCode123";
    DeliveryOrder mockOrder = mock(DeliveryOrder.class);
    when(orderService.findOrderOrElseThrow(orderCode)).thenReturn(mockOrder);

    // Act
    orderFlowService.deliverOrder(orderCode);

    // Assert
    verify(orderService).findOrderOrElseThrow(orderCode);
    verify(mockOrder).deliver();
  }

  @Test
  void testCancelOrder() {
    // Arrange
    String orderCode = "orderCode123";
    DeliveryOrder mockOrder = mock(DeliveryOrder.class);
    when(orderService.findOrderOrElseThrow(orderCode)).thenReturn(mockOrder);

    // Act
    orderFlowService.cancelOrder(orderCode);

    // Assert
    verify(orderService).findOrderOrElseThrow(orderCode);
    verify(mockOrder).cancel();
  }
}
