package br.com.allen.flashfood.domain.listener;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.allen.flashfood.domain.event.CancelledOrderEvent;
import br.com.allen.flashfood.domain.model.DeliveryOrder;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.model.User;
import br.com.allen.flashfood.domain.service.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CancelledOrderNotificationListenerTest {

  @Mock private EmailSenderService emailService;

  @InjectMocks private CancelledOrderNotificationListener listener;

  @Test
  void whenOrderCancelledSendsEmail() {
    // Arrange
    DeliveryOrder mockOrder = mock(DeliveryOrder.class);
    Restaurant mockRestaurant = mock(Restaurant.class);
    User mockUser = mock(User.class);

    when(mockOrder.getRestaurant()).thenReturn(mockRestaurant);
    when(mockOrder.getUser()).thenReturn(mockUser);
    when(mockRestaurant.getName()).thenReturn("Test Restaurant");
    when(mockUser.getEmail()).thenReturn("user@example.com");

    CancelledOrderEvent event = new CancelledOrderEvent(mockOrder);

    // Act
    listener.whenOrderCancelled(event);

    // Assert
    verify(emailService).send(any(EmailSenderService.EmailMessage.class));
  }
}
