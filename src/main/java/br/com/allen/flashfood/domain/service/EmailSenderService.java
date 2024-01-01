package br.com.allen.flashfood.domain.service;

import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EmailSenderService {
  void send(EmailMessage message);

  @Getter
  @Builder
  class EmailMessage {
    @Singular("to")
    private Set<String> to;
    @NonNull private String subject;
    @NonNull private String body;
    @Singular private Map<String, Object> variables;
  }
}
