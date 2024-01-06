package br.com.allen.flashfood.core.openapi;

import br.com.allen.flashfood.api.exceptionhandler.ApiError;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

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

  @Bean
  public OpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {
    return openApi ->
        openApi
            .getPaths()
            .forEach(
                (path, pathItem) -> {
                  applyCustomResponsesForGet(pathItem.getGet());
                  applyCustomResponsesForPostAndPut(pathItem.getPost());
                  applyCustomResponsesForDelete(pathItem.getDelete());
                });
  }

  @Bean
  public OpenApiCustomizer schemaCustomizer() {
    ResolvedSchema resolvedSchemaApiError =
        ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(ApiError.class));

    ResolvedSchema resolvedSchemaAnotherModel =
        ModelConverters.getInstance()
            .resolveAsResolvedSchema(new AnnotatedType(ApiError.Field.class));

    return openApi -> {
      Map<String, Schema> schemas = openApi.getComponents().getSchemas();
      if (schemas == null) {
        schemas = new HashMap<>();
        openApi.getComponents().setSchemas(schemas);
      }

      schemas.put(resolvedSchemaApiError.schema.getName(), resolvedSchemaApiError.schema);
      schemas.put(resolvedSchemaAnotherModel.schema.getName(), resolvedSchemaAnotherModel.schema);
    };
  }

  private void applyCustomResponsesForGet(Operation operation) {
    if (operation != null) {
      ApiResponses apiResponses = operation.getResponses();
      apiResponses.addApiResponse(
          "406",
          createApiResponse(
              "The resource doesn't have a representation that could be accepted by the consumer"));
      apiResponses.addApiResponse("500", createApiResponse("Internal Server Error", true));
    }
  }

  private void applyCustomResponsesForPostAndPut(Operation operation) {
    if (operation != null) {
      ApiResponses apiResponses = operation.getResponses();
      apiResponses.addApiResponse("400", createApiResponse("Invalid request (client error)", true));
      apiResponses.addApiResponse("500", createApiResponse("Internal Server Error", true));
      apiResponses.addApiResponse(
          "406",
          createApiResponse(
              "The resource doesn't have a representation that could be accepted by the consumer"));
      apiResponses.addApiResponse(
          "415",
          createApiResponse("Request refused because the body is in an unsupported format", true));
    }
  }

  private void applyCustomResponsesForDelete(Operation operation) {
    if (operation != null) {
      ApiResponses apiResponses = operation.getResponses();
      apiResponses.addApiResponse("400", createApiResponse("Invalid request (client error)", true));
      apiResponses.addApiResponse("500", createApiResponse("Internal Server Error", true));
    }
  }

  private ApiResponse createApiResponse(String description) {
    return new ApiResponse()
        .description(description)
        .content(
            new Content()
                .addMediaType(
                    org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType()));
  }

  private ApiResponse createApiResponse(String description, boolean isErrorResponse) {
    ApiResponse apiResponse = new ApiResponse().description(description);
    Content content = new Content();
    MediaType mediaType = new MediaType();

    if (isErrorResponse) {
      Schema<?> errorSchema = new Schema<>().$ref("API Error");
      mediaType.setSchema(errorSchema);
    }

    content.addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, mediaType);
    apiResponse.setContent(content);
    return apiResponse;
  }
}