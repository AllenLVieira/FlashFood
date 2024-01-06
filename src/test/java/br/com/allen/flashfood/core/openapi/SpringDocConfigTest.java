package br.com.allen.flashfood.core.openapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springdoc.core.customizers.OpenApiCustomizer;

class SpringDocConfigTest {
  /** Method under test: {@link SpringDocConfig#openAPI()} */
  @Test
  void testOpenAPI() {

    // Arrange and Act
    OpenAPI actualOpenAPIResult = (new SpringDocConfig()).openAPI();

    // Assert
    Info info = actualOpenAPIResult.getInfo();
    assertEquals("1.0", info.getVersion());
    assertEquals("3.0.1", actualOpenAPIResult.getOpenapi());
    Contact contact = info.getContact();
    assertEquals("Allen Vieira", contact.getName());
    License license = info.getLicense();
    assertEquals("Apache 2.0", license.getName());
    assertEquals("Flashfood - Rest API", info.getTitle());
    List<Server> servers = actualOpenAPIResult.getServers();
    assertEquals(3, servers.size());
    Server getResult = servers.get(0);
    assertEquals("Local Development Server", getResult.getDescription());
    ExternalDocumentation externalDocs = actualOpenAPIResult.getExternalDocs();
    assertEquals("More about Allen Vieira", externalDocs.getDescription());
    assertEquals("OpenAPI - Flashfood", info.getDescription());
    Server getResult2 = servers.get(2);
    assertEquals("Production Server", getResult2.getDescription());
    Server getResult3 = servers.get(1);
    assertEquals("Staging Server", getResult3.getDescription());
    assertEquals("allenvieira96@gmail.com", contact.getEmail());
    assertEquals("http://localhost:8080", getResult.getUrl());
    assertEquals("https://api.flashfood.com", getResult2.getUrl());
    assertEquals("https://api.staging.flashfood.com", getResult3.getUrl());
    assertEquals("https://www.linkedin.com/in/allen-vieira/", externalDocs.getUrl());
    assertEquals("https://www.linkedin.com/in/allen-vieira/", contact.getUrl());
    assertEquals("https://www.linkedin.com/in/allen-vieira/", license.getUrl());
    assertEquals(SpecVersion.V30, actualOpenAPIResult.getSpecVersion());
  }

  /** Method under test: {@link SpringDocConfig#customerGlobalHeaderOpenApiCustomiser()} */
  @Test
  void testCustomerGlobalHeaderOpenApiCustomiser() {

    // Arrange and Act
    OpenApiCustomizer actualCustomerGlobalHeaderOpenApiCustomiserResult =
        (new SpringDocConfig()).customerGlobalHeaderOpenApiCustomiser();
    OpenAPI openApi = mock(OpenAPI.class);
    when(openApi.getPaths()).thenReturn(new Paths());
    actualCustomerGlobalHeaderOpenApiCustomiserResult.customise(openApi);

    // Assert that nothing has changed
    verify(openApi).getPaths();
  }

  /** Method under test: {@link SpringDocConfig#customerGlobalHeaderOpenApiCustomiser()} */
  @Test
  void testCustomerGlobalHeaderOpenApiCustomiser2() {

    // Arrange and Act
    OpenApiCustomizer actualCustomerGlobalHeaderOpenApiCustomiserResult =
        (new SpringDocConfig()).customerGlobalHeaderOpenApiCustomiser();
    Paths paths = new Paths();
    paths.addPathItem("Name", new PathItem());
    OpenAPI openApi = mock(OpenAPI.class);
    when(openApi.getPaths()).thenReturn(paths);
    actualCustomerGlobalHeaderOpenApiCustomiserResult.customise(openApi);

    // Assert that nothing has changed
    verify(openApi).getPaths();
  }

  /** Method under test: {@link SpringDocConfig#customerGlobalHeaderOpenApiCustomiser()} */
  @Test
  void testCustomerGlobalHeaderOpenApiCustomiser3() {

    // Arrange and Act
    OpenApiCustomizer actualCustomerGlobalHeaderOpenApiCustomiserResult =
        (new SpringDocConfig()).customerGlobalHeaderOpenApiCustomiser();
    Paths paths = new Paths();
    paths.addPathItem("42", new PathItem());
    paths.addPathItem("Name", new PathItem());
    OpenAPI openApi = mock(OpenAPI.class);
    when(openApi.getPaths()).thenReturn(paths);
    actualCustomerGlobalHeaderOpenApiCustomiserResult.customise(openApi);

    // Assert that nothing has changed
    verify(openApi).getPaths();
  }

  /** Method under test: {@link SpringDocConfig#customerGlobalHeaderOpenApiCustomiser()} */
  @Test
  void testCustomerGlobalHeaderOpenApiCustomiser4() {

    // Arrange and Act
    OpenApiCustomizer actualCustomerGlobalHeaderOpenApiCustomiserResult =
        (new SpringDocConfig()).customerGlobalHeaderOpenApiCustomiser();
    Operation operation = mock(Operation.class);
    when(operation.getResponses()).thenReturn(new ApiResponses());
    Operation operation2 = mock(Operation.class);
    when(operation2.getResponses()).thenReturn(new ApiResponses());
    Operation operation3 = mock(Operation.class);
    when(operation3.getResponses()).thenReturn(new ApiResponses());
    Operation operation4 = mock(Operation.class);
    when(operation4.getResponses()).thenReturn(new ApiResponses());
    PathItem item = mock(PathItem.class);
    when(item.getDelete()).thenReturn(operation);
    when(item.getGet()).thenReturn(operation2);
    when(item.getPost()).thenReturn(operation3);
    when(item.getPut()).thenReturn(operation4);
    Paths paths = new Paths();
    paths.addPathItem("Name", item);
    OpenAPI openApi = mock(OpenAPI.class);
    when(openApi.getPaths()).thenReturn(paths);
    actualCustomerGlobalHeaderOpenApiCustomiserResult.customise(openApi);

    // Assert
    verify(openApi).getPaths();
    verify(operation).getResponses();
    verify(operation2).getResponses();
    verify(operation3).getResponses();
    verify(operation4).getResponses();
    verify(item, atLeast(1)).getDelete();
    verify(item, atLeast(1)).getGet();
    verify(item, atLeast(1)).getPost();
    verify(item, atLeast(1)).getPut();
  }

  /** Method under test: {@link SpringDocConfig#schemaCustomizer()} */
  @Test
  void testSchemaCustomizer() {

    // Arrange and Act
    OpenApiCustomizer actualSchemaCustomizerResult = (new SpringDocConfig()).schemaCustomizer();
    OpenAPI openApi = new OpenAPI();
    openApi.setComponents(new Components());
    actualSchemaCustomizerResult.customise(openApi);

    // Assert
    assertEquals(2, openApi.getComponents().getSchemas().size());
  }

  /** Method under test: {@link SpringDocConfig#schemaCustomizer()} */
  @Test
  void testSchemaCustomizer2() {

    // Arrange and Act
    OpenApiCustomizer actualSchemaCustomizerResult = (new SpringDocConfig()).schemaCustomizer();
    OpenAPI openApi = new OpenAPI();
    openApi.schema("Name", new Schema());
    actualSchemaCustomizerResult.customise(openApi);

    // Assert
    assertEquals(3, openApi.getComponents().getSchemas().size());
  }

  /** Method under test: {@link SpringDocConfig#schemaCustomizer()} */
  @Test
  void testSchemaCustomizer3() {

    // Arrange and Act
    OpenApiCustomizer actualSchemaCustomizerResult = (new SpringDocConfig()).schemaCustomizer();
    OpenAPI openApi = mock(OpenAPI.class);
    when(openApi.getComponents()).thenReturn(new Components());
    actualSchemaCustomizerResult.customise(openApi);

    // Assert
    verify(openApi, atLeast(1)).getComponents();
  }
}
