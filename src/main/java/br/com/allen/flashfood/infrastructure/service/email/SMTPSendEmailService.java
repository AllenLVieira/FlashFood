package br.com.allen.flashfood.infrastructure.service.email;

import br.com.allen.flashfood.core.email.EmailProperties;
import br.com.allen.flashfood.domain.service.EmailSenderService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
@RequiredArgsConstructor
public class SMTPSendEmailService implements EmailSenderService {

  private final JavaMailSender mailSender;
  private final EmailProperties emailProperties;
  private final Configuration freemarkerConfiguration;

  @Override
  public void send(EmailMessage message) {
    try {
      String body = processTemplate(message);
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

      mimeHelper.setFrom(emailProperties.getFrom());
      mimeHelper.setSubject(message.getSubject());
      mimeHelper.setText(body, true);
      mimeHelper.setTo(message.getTo().toArray(new String[0]));

      mailSender.send(mimeMessage);
    } catch (Exception e) {
      throw new EmailException("Failed to send email.", e);
    }
  }

  private String processTemplate(EmailMessage message) {
    try {
      Template template = freemarkerConfiguration.getTemplate(message.getBody());
      return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
    } catch (Exception e) {
      throw new EmailException("Failed to process email template.", e);
    }
  }
}
