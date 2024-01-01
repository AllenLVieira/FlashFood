package br.com.allen.flashfood.core.email;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties("flashfood.email")
@Getter
@Setter
@Validated
public class EmailProperties {
  @NotNull private String from;
}
