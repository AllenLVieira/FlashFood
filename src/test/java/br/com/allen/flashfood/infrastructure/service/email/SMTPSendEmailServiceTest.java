package br.com.allen.flashfood.infrastructure.service.email;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.allen.flashfood.core.email.EmailProperties;
import br.com.allen.flashfood.domain.service.EmailSenderService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import java.io.Writer;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class SMTPSendEmailServiceTest {

  @Mock private JavaMailSender mailSender;

  @Mock private EmailProperties emailProperties;

  @Mock private Configuration freemarkerConfiguration;

  @InjectMocks private SMTPSendEmailService smtpSendEmailService;

  @Mock private Template template;

  @BeforeEach
  void setUp() throws Exception {
    when(freemarkerConfiguration.getTemplate(anyString())).thenReturn(template);

    doAnswer(
            (Answer<Void>)
                invocation -> {
                  Writer out = invocation.getArgument(1);
                  out.write("Processed Email Body");
                  return null;
                })
        .when(template)
        .process(any(), any(Writer.class));

    when(emailProperties.getFrom()).thenReturn("from@example.com");
  }

  @Test
  void sendEmailSuccess() throws Exception {
    // Arrange
    EmailSenderService.EmailMessage message =
        EmailSenderService.EmailMessage.builder()
            .subject("Test Subject")
            .body("template.ftl")
            .variable("key", "value")
            .to(Set.of("test@example.com"))
            .build();
    MimeMessage mimeMessage = mock(MimeMessage.class);
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

    // Act
    smtpSendEmailService.send(message);

    // Assert
    verify(mailSender).send(mimeMessage);
  }

  @Test
  void sendEmailThrowsEmailException() {
    // Arrange
    EmailSenderService.EmailMessage message =
        EmailSenderService.EmailMessage.builder()
            .subject("Test Subject")
            .body("template.ftl")
            .variable("key", "value")
            .to(Set.of("test@example.com"))
            .build();

    MimeMessage mimeMessage = mock(MimeMessage.class);
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

    // Throw a runtime exception to simulate the error
    doThrow(new RuntimeException("Test exception")).when(mailSender).send(mimeMessage);

    // Act & Assert
    assertThrows(EmailException.class, () -> smtpSendEmailService.send(message));
  }
}
