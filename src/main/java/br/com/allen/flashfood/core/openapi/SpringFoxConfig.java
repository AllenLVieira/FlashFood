package br.com.allen.flashfood.core.openapi;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringFoxConfig {

  // Constants or externalize these as properties
  private static final String API_TITLE = "Flashfood - Rest API";
  private static final String API_DESCRIPTION = "OpenAPI - Flashfood";
  private static final String API_VERSION = "1.0";
  private static final String LICENSE_NAME = "Apache 2.0";
  private static final String LICENSE_URL = "https://www.linkedin.com/in/allen-vieira/";
  private static final String EXTERNAL_DOC_DESC = "More about Allen Vieira";
  private static final String EXTERNAL_DOC_URL = "https://www.linkedin.com/in/allen-vieira/";
  private static final String CONTACT_NAME = "Allen Vieira";
  private static final String CONTACT_MAIL = "allenvieira96@gmail.com";

  @Bean
  public OpenAPI openAPI() {

    return new OpenAPI()
        .info(
            new Info()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .contact(new Contact().url(EXTERNAL_DOC_URL).name(CONTACT_NAME).email(CONTACT_MAIL))
                .license(new License().name(LICENSE_NAME).url(LICENSE_URL)))
        .externalDocs(
            new ExternalDocumentation().description(EXTERNAL_DOC_DESC).url(EXTERNAL_DOC_URL))
        .servers(
            Arrays.asList(
                new Server().url("http://localhost:8080").description("Local Development Server"),
                new Server().url("https://api.staging.flashfood.com").description("Staging Server"),
                new Server().url("https://api.flashfood.com").description("Production Server")));
  }
}