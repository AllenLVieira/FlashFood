package br.com.allen.flashfood.api.exceptionhandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.CityNotFoundException;
import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import java.beans.PropertyChangeEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ContextConfiguration(classes = {ApiExceptionHandler.class, MessageSource.class})
@ExtendWith(SpringExtension.class)
class ApiExceptionHandlerTest {
  @Autowired private ApiExceptionHandler apiExceptionHandler;

  @MockBean private MessageSource messageSource;

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleEntityNotFoundedException(EntityNotFoundedException, WebRequest)}
   */
  @Test
  void testHandleEntityNotFoundedException() {
    CityNotFoundException ex = new CityNotFoundException("An error occurred");
    ResponseEntity<?> actualHandleEntityNotFoundedExceptionResult =
        apiExceptionHandler.handleEntityNotFoundedException(
            ex, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleEntityNotFoundedExceptionResult.hasBody());
    assertTrue(actualHandleEntityNotFoundedExceptionResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.NOT_FOUND, actualHandleEntityNotFoundedExceptionResult.getStatusCode());
    assertEquals(
        "Resource not found",
        ((ApiError) actualHandleEntityNotFoundedExceptionResult.getBody()).getTitle());
    assertEquals(
        404,
        ((ApiError) actualHandleEntityNotFoundedExceptionResult.getBody()).getStatus().intValue());
    assertNull(((ApiError) actualHandleEntityNotFoundedExceptionResult.getBody()).getUserMessage());
    assertEquals(
        "An error occurred",
        ((ApiError) actualHandleEntityNotFoundedExceptionResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleEntityNotFoundedExceptionResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/resource-not-found",
        ((ApiError) actualHandleEntityNotFoundedExceptionResult.getBody()).getType());
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleEntityNotFoundedException(EntityNotFoundedException, WebRequest)}
   */
  @Test
  void testHandleEntityNotFoundedException3() {
    EntityNotFoundedException ex = mock(EntityNotFoundedException.class);
    when(ex.getMessage()).thenReturn("Not all who wander are lost");
    ResponseEntity<?> actualHandleEntityNotFoundedExceptionResult =
        apiExceptionHandler.handleEntityNotFoundedException(
            ex, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleEntityNotFoundedExceptionResult.hasBody());
    assertTrue(actualHandleEntityNotFoundedExceptionResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.NOT_FOUND, actualHandleEntityNotFoundedExceptionResult.getStatusCode());
    assertEquals(
        "Resource not found",
        ((ApiError) actualHandleEntityNotFoundedExceptionResult.getBody()).getTitle());
    assertEquals(
        404,
        ((ApiError) actualHandleEntityNotFoundedExceptionResult.getBody()).getStatus().intValue());
    assertNull(((ApiError) actualHandleEntityNotFoundedExceptionResult.getBody()).getUserMessage());
    assertEquals(
        "Not all who wander are lost",
        ((ApiError) actualHandleEntityNotFoundedExceptionResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleEntityNotFoundedExceptionResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/resource-not-found",
        ((ApiError) actualHandleEntityNotFoundedExceptionResult.getBody()).getType());
    verify(ex).getMessage();
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleEntityInUseException(EntityInUseException,
   * WebRequest)}
   */
  @Test
  void testHandleEntityInUseException() {
    EntityInUseException ex = new EntityInUseException("An error occurred");
    ResponseEntity<?> actualHandleEntityInUseExceptionResult =
        apiExceptionHandler.handleEntityInUseException(
            ex, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleEntityInUseExceptionResult.hasBody());
    assertTrue(actualHandleEntityInUseExceptionResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONFLICT, actualHandleEntityInUseExceptionResult.getStatusCode());
    assertEquals(
        "Entity in use", ((ApiError) actualHandleEntityInUseExceptionResult.getBody()).getTitle());
    assertEquals(
        409, ((ApiError) actualHandleEntityInUseExceptionResult.getBody()).getStatus().intValue());
    assertEquals(
        "An error occurred",
        ((ApiError) actualHandleEntityInUseExceptionResult.getBody()).getUserMessage());
    assertEquals(
        "An error occurred",
        ((ApiError) actualHandleEntityInUseExceptionResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleEntityInUseExceptionResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/entity-in-use",
        ((ApiError) actualHandleEntityInUseExceptionResult.getBody()).getType());
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleEntityInUseException(EntityInUseException,
   * WebRequest)}
   */
  @Test
  void testHandleEntityInUseException3() {
    EntityInUseException ex = mock(EntityInUseException.class);
    when(ex.getMessage()).thenReturn("Not all who wander are lost");
    ResponseEntity<?> actualHandleEntityInUseExceptionResult =
        apiExceptionHandler.handleEntityInUseException(
            ex, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleEntityInUseExceptionResult.hasBody());
    assertTrue(actualHandleEntityInUseExceptionResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONFLICT, actualHandleEntityInUseExceptionResult.getStatusCode());
    assertEquals(
        "Entity in use", ((ApiError) actualHandleEntityInUseExceptionResult.getBody()).getTitle());
    assertEquals(
        409, ((ApiError) actualHandleEntityInUseExceptionResult.getBody()).getStatus().intValue());
    assertEquals(
        "Not all who wander are lost",
        ((ApiError) actualHandleEntityInUseExceptionResult.getBody()).getUserMessage());
    assertEquals(
        "Not all who wander are lost",
        ((ApiError) actualHandleEntityInUseExceptionResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleEntityInUseExceptionResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/entity-in-use",
        ((ApiError) actualHandleEntityInUseExceptionResult.getBody()).getType());
    verify(ex).getMessage();
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleBusinessException(BusinessException,
   * WebRequest)}
   */
  @Test
  void testHandleBusinessException() {
    BusinessException ex = new BusinessException("An error occurred");
    ResponseEntity<?> actualHandleBusinessExceptionResult =
        apiExceptionHandler.handleBusinessException(
            ex, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleBusinessExceptionResult.hasBody());
    assertTrue(actualHandleBusinessExceptionResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.BAD_REQUEST, actualHandleBusinessExceptionResult.getStatusCode());
    assertEquals(
        "Business rule violation",
        ((ApiError) actualHandleBusinessExceptionResult.getBody()).getTitle());
    assertEquals(
        400, ((ApiError) actualHandleBusinessExceptionResult.getBody()).getStatus().intValue());
    assertEquals(
        "An error occurred",
        ((ApiError) actualHandleBusinessExceptionResult.getBody()).getUserMessage());
    assertEquals(
        "An error occurred",
        ((ApiError) actualHandleBusinessExceptionResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleBusinessExceptionResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/business-error",
        ((ApiError) actualHandleBusinessExceptionResult.getBody()).getType());
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleBusinessException(BusinessException,
   * WebRequest)}
   */
  @Test
  void testHandleBusinessException3() {
    BusinessException ex = mock(BusinessException.class);
    when(ex.getMessage()).thenReturn("Not all who wander are lost");
    ResponseEntity<?> actualHandleBusinessExceptionResult =
        apiExceptionHandler.handleBusinessException(
            ex, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleBusinessExceptionResult.hasBody());
    assertTrue(actualHandleBusinessExceptionResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.BAD_REQUEST, actualHandleBusinessExceptionResult.getStatusCode());
    assertEquals(
        "Business rule violation",
        ((ApiError) actualHandleBusinessExceptionResult.getBody()).getTitle());
    assertEquals(
        400, ((ApiError) actualHandleBusinessExceptionResult.getBody()).getStatus().intValue());
    assertEquals(
        "Not all who wander are lost",
        ((ApiError) actualHandleBusinessExceptionResult.getBody()).getUserMessage());
    assertEquals(
        "Not all who wander are lost",
        ((ApiError) actualHandleBusinessExceptionResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleBusinessExceptionResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/business-error",
        ((ApiError) actualHandleBusinessExceptionResult.getBody()).getType());
    verify(ex).getMessage();
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleExceptionInternal(Exception, Object,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleExceptionInternal() {
    Exception ex = new Exception("An error occurred");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleExceptionInternalResult =
        apiExceptionHandler.handleExceptionInternal(
            ex,
            "Body",
            headers,
            HttpStatus.CONTINUE,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleExceptionInternalResult.hasBody());
    assertTrue(actualHandleExceptionInternalResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONTINUE, actualHandleExceptionInternalResult.getStatusCode());
    assertEquals("Body", ((ApiError) actualHandleExceptionInternalResult.getBody()).getTitle());
    assertEquals(
        100, ((ApiError) actualHandleExceptionInternalResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleExceptionInternalResult.getBody()).getUserMessage());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getFields());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getType());
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleExceptionInternal(Exception, Object,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleExceptionInternal2() {
    Exception ex = new Exception("An error occurred");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleExceptionInternalResult =
        apiExceptionHandler.handleExceptionInternal(
            ex,
            1,
            headers,
            HttpStatus.CONTINUE,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleExceptionInternalResult.hasBody());
    assertEquals(HttpStatus.CONTINUE, actualHandleExceptionInternalResult.getStatusCode());
    assertTrue(actualHandleExceptionInternalResult.getHeaders().isEmpty());
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleExceptionInternal(Exception, Object,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleExceptionInternal3() {
    Exception ex = new Exception("An error occurred");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleExceptionInternalResult =
        apiExceptionHandler.handleExceptionInternal(
            ex,
            null,
            headers,
            HttpStatus.CONTINUE,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleExceptionInternalResult.hasBody());
    assertTrue(actualHandleExceptionInternalResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONTINUE, actualHandleExceptionInternalResult.getStatusCode());
    assertEquals("Continue", ((ApiError) actualHandleExceptionInternalResult.getBody()).getTitle());
    assertEquals(
        100, ((ApiError) actualHandleExceptionInternalResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleExceptionInternalResult.getBody()).getUserMessage());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getFields());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getType());
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleExceptionInternal(Exception, Object,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleExceptionInternal5() {
    Exception ex = new Exception("An error occurred");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleExceptionInternalResult =
        apiExceptionHandler.handleExceptionInternal(
            ex,
            "Body",
            headers,
            HttpStatus.SWITCHING_PROTOCOLS,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleExceptionInternalResult.hasBody());
    assertTrue(actualHandleExceptionInternalResult.getHeaders().isEmpty());
    assertEquals(
        HttpStatus.SWITCHING_PROTOCOLS, actualHandleExceptionInternalResult.getStatusCode());
    assertEquals("Body", ((ApiError) actualHandleExceptionInternalResult.getBody()).getTitle());
    assertEquals(
        101, ((ApiError) actualHandleExceptionInternalResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleExceptionInternalResult.getBody()).getUserMessage());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getFields());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getType());
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleExceptionInternal(Exception, Object,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleExceptionInternal6() {
    Exception ex = new Exception("An error occurred");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleExceptionInternalResult =
        apiExceptionHandler.handleExceptionInternal(
            ex,
            "Body",
            headers,
            HttpStatus.PROCESSING,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleExceptionInternalResult.hasBody());
    assertTrue(actualHandleExceptionInternalResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.PROCESSING, actualHandleExceptionInternalResult.getStatusCode());
    assertEquals("Body", ((ApiError) actualHandleExceptionInternalResult.getBody()).getTitle());
    assertEquals(
        102, ((ApiError) actualHandleExceptionInternalResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleExceptionInternalResult.getBody()).getUserMessage());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getFields());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getType());
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleExceptionInternal(Exception, Object,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleExceptionInternal7() {
    Exception ex = new Exception("An error occurred");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleExceptionInternalResult =
        apiExceptionHandler.handleExceptionInternal(
            ex,
            "Body",
            headers,
            HttpStatus.CHECKPOINT,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleExceptionInternalResult.hasBody());
    assertTrue(actualHandleExceptionInternalResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CHECKPOINT, actualHandleExceptionInternalResult.getStatusCode());
    assertEquals("Body", ((ApiError) actualHandleExceptionInternalResult.getBody()).getTitle());
    assertEquals(
        103, ((ApiError) actualHandleExceptionInternalResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleExceptionInternalResult.getBody()).getUserMessage());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getFields());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getType());
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleExceptionInternal(Exception, Object,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleExceptionInternal8() {
    Exception ex = new Exception("An error occurred");
    ResponseEntity<Object> actualHandleExceptionInternalResult =
        apiExceptionHandler.handleExceptionInternal(
            ex, "Body", new HttpHeaders(), HttpStatus.CONTINUE, mock(WebRequest.class));
    assertTrue(actualHandleExceptionInternalResult.hasBody());
    assertTrue(actualHandleExceptionInternalResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONTINUE, actualHandleExceptionInternalResult.getStatusCode());
    assertEquals("Body", ((ApiError) actualHandleExceptionInternalResult.getBody()).getTitle());
    assertEquals(
        100, ((ApiError) actualHandleExceptionInternalResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleExceptionInternalResult.getBody()).getUserMessage());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getFields());
    assertNull(((ApiError) actualHandleExceptionInternalResult.getBody()).getType());
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleHttpMessageNotReadable(HttpMessageNotReadableException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleHttpMessageNotReadable() {
    HttpMessageNotReadableException ex =
        new HttpMessageNotReadableException("https://example.org/example");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleHttpMessageNotReadableResult =
        apiExceptionHandler.handleHttpMessageNotReadable(
            ex, headers, HttpStatus.CONTINUE, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleHttpMessageNotReadableResult.hasBody());
    assertTrue(actualHandleHttpMessageNotReadableResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONTINUE, actualHandleHttpMessageNotReadableResult.getStatusCode());
    assertEquals(
        "Message not readable",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getTitle());
    assertEquals(
        100,
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getUserMessage());
    assertEquals(
        "The body of the request is invalid. Please check for syntax errors.",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/message-not-readable",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getType());
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleHttpMessageNotReadable(HttpMessageNotReadableException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleHttpMessageNotReadable2() {
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleHttpMessageNotReadableResult =
        apiExceptionHandler.handleHttpMessageNotReadable(
            null,
            headers,
            HttpStatus.CONTINUE,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleHttpMessageNotReadableResult.hasBody());
    assertTrue(actualHandleHttpMessageNotReadableResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONTINUE, actualHandleHttpMessageNotReadableResult.getStatusCode());
    assertEquals(
        "Message not readable",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getTitle());
    assertEquals(
        100,
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getUserMessage());
    assertEquals(
        "The body of the request is invalid. Please check for syntax errors.",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/message-not-readable",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getType());
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleHttpMessageNotReadable(HttpMessageNotReadableException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleHttpMessageNotReadable3() {
    HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
    when(ex.getCause()).thenReturn(new Throwable());
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleHttpMessageNotReadableResult =
        apiExceptionHandler.handleHttpMessageNotReadable(
            ex, headers, HttpStatus.CONTINUE, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleHttpMessageNotReadableResult.hasBody());
    assertTrue(actualHandleHttpMessageNotReadableResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONTINUE, actualHandleHttpMessageNotReadableResult.getStatusCode());
    assertEquals(
        "Message not readable",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getTitle());
    assertEquals(
        100,
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getUserMessage());
    assertEquals(
        "The body of the request is invalid. Please check for syntax errors.",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/message-not-readable",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getType());
    verify(ex).getCause();
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleHttpMessageNotReadable(HttpMessageNotReadableException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleHttpMessageNotReadable5() {
    HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
    when(ex.getCause()).thenReturn(new Throwable());
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleHttpMessageNotReadableResult =
        apiExceptionHandler.handleHttpMessageNotReadable(
            ex,
            headers,
            HttpStatus.SWITCHING_PROTOCOLS,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleHttpMessageNotReadableResult.hasBody());
    assertTrue(actualHandleHttpMessageNotReadableResult.getHeaders().isEmpty());
    assertEquals(
        HttpStatus.SWITCHING_PROTOCOLS, actualHandleHttpMessageNotReadableResult.getStatusCode());
    assertEquals(
        "Message not readable",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getTitle());
    assertEquals(
        101,
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getUserMessage());
    assertEquals(
        "The body of the request is invalid. Please check for syntax errors.",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/message-not-readable",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getType());
    verify(ex).getCause();
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleHttpMessageNotReadable(HttpMessageNotReadableException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleHttpMessageNotReadable6() {
    HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
    when(ex.getCause()).thenReturn(new Throwable());
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleHttpMessageNotReadableResult =
        apiExceptionHandler.handleHttpMessageNotReadable(
            ex,
            headers,
            HttpStatus.PROCESSING,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleHttpMessageNotReadableResult.hasBody());
    assertTrue(actualHandleHttpMessageNotReadableResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.PROCESSING, actualHandleHttpMessageNotReadableResult.getStatusCode());
    assertEquals(
        "Message not readable",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getTitle());
    assertEquals(
        102,
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getUserMessage());
    assertEquals(
        "The body of the request is invalid. Please check for syntax errors.",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/message-not-readable",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getType());
    verify(ex).getCause();
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleHttpMessageNotReadable(HttpMessageNotReadableException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleHttpMessageNotReadable7() {
    HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
    when(ex.getCause()).thenReturn(new Throwable());
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleHttpMessageNotReadableResult =
        apiExceptionHandler.handleHttpMessageNotReadable(
            ex,
            headers,
            HttpStatus.CHECKPOINT,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleHttpMessageNotReadableResult.hasBody());
    assertTrue(actualHandleHttpMessageNotReadableResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CHECKPOINT, actualHandleHttpMessageNotReadableResult.getStatusCode());
    assertEquals(
        "Message not readable",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getTitle());
    assertEquals(
        103,
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getUserMessage());
    assertEquals(
        "The body of the request is invalid. Please check for syntax errors.",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/message-not-readable",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getType());
    verify(ex).getCause();
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleHttpMessageNotReadable(HttpMessageNotReadableException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleHttpMessageNotReadable8() {
    HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
    when(ex.getCause()).thenReturn(new Throwable());
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleHttpMessageNotReadableResult =
        apiExceptionHandler.handleHttpMessageNotReadable(
            ex, headers, HttpStatus.OK, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleHttpMessageNotReadableResult.hasBody());
    assertTrue(actualHandleHttpMessageNotReadableResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.OK, actualHandleHttpMessageNotReadableResult.getStatusCode());
    assertEquals(
        "Message not readable",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getTitle());
    assertEquals(
        200,
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getUserMessage());
    assertEquals(
        "The body of the request is invalid. Please check for syntax errors.",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/message-not-readable",
        ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getType());
    verify(ex).getCause();
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleTypeMismatch(TypeMismatchException,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleTypeMismatch() {
    PropertyChangeEvent propertyChangeEvent =
        new PropertyChangeEvent("Source", "Property Name", "Old Value", "New Value");

    TypeMismatchException ex = new TypeMismatchException(propertyChangeEvent, Object.class);

    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleTypeMismatchResult =
        apiExceptionHandler.handleTypeMismatch(
            ex, headers, HttpStatus.CONTINUE, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleTypeMismatchResult.hasBody());
    assertTrue(actualHandleTypeMismatchResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONTINUE, actualHandleTypeMismatchResult.getStatusCode());
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleTypeMismatch(TypeMismatchException,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleTypeMismatch2() {
    PropertyChangeEvent propertyChangeEvent =
        new PropertyChangeEvent(
            mock(MethodArgumentTypeMismatchException.class),
            "Property Name",
            "Old Value",
            "New Value");

    TypeMismatchException ex = new TypeMismatchException(propertyChangeEvent, Object.class);

    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleTypeMismatchResult =
        apiExceptionHandler.handleTypeMismatch(
            ex, headers, HttpStatus.CONTINUE, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleTypeMismatchResult.hasBody());
    assertTrue(actualHandleTypeMismatchResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONTINUE, actualHandleTypeMismatchResult.getStatusCode());
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleTypeMismatch(TypeMismatchException,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleTypeMismatch3() {
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
    when(ex.getValue()).thenReturn("Value");
    when(ex.getName()).thenReturn("0123456789ABCDEF");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleTypeMismatchResult =
        apiExceptionHandler.handleTypeMismatch(
            ex, headers, HttpStatus.CONTINUE, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleTypeMismatchResult.hasBody());
    assertTrue(actualHandleTypeMismatchResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONTINUE, actualHandleTypeMismatchResult.getStatusCode());
    assertEquals(
        "Invalid parameter", ((ApiError) actualHandleTypeMismatchResult.getBody()).getTitle());
    assertEquals(100, ((ApiError) actualHandleTypeMismatchResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getUserMessage());
    assertEquals(
        "The URL parameter '0123456789ABCDEF' received the value 'Value', which is of an invalid type. Correct"
            + " and try again.",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleTypeMismatchResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/invalid-parameter",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getType());
    verify(ex).getValue();
    verify(ex).getName();
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleTypeMismatch(TypeMismatchException,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleTypeMismatch5() {
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
    when(ex.getValue()).thenReturn("Value");
    when(ex.getName()).thenReturn("0123456789ABCDEF");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleTypeMismatchResult =
        apiExceptionHandler.handleTypeMismatch(
            ex,
            headers,
            HttpStatus.SWITCHING_PROTOCOLS,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleTypeMismatchResult.hasBody());
    assertTrue(actualHandleTypeMismatchResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.SWITCHING_PROTOCOLS, actualHandleTypeMismatchResult.getStatusCode());
    assertEquals(
        "Invalid parameter", ((ApiError) actualHandleTypeMismatchResult.getBody()).getTitle());
    assertEquals(101, ((ApiError) actualHandleTypeMismatchResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getUserMessage());
    assertEquals(
        "The URL parameter '0123456789ABCDEF' received the value 'Value', which is of an invalid type. Correct"
            + " and try again.",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleTypeMismatchResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/invalid-parameter",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getType());
    verify(ex).getValue();
    verify(ex).getName();
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleTypeMismatch(TypeMismatchException,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleTypeMismatch6() {
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
    when(ex.getValue()).thenReturn("Value");
    when(ex.getName()).thenReturn("0123456789ABCDEF");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleTypeMismatchResult =
        apiExceptionHandler.handleTypeMismatch(
            ex,
            headers,
            HttpStatus.PROCESSING,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleTypeMismatchResult.hasBody());
    assertTrue(actualHandleTypeMismatchResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.PROCESSING, actualHandleTypeMismatchResult.getStatusCode());
    assertEquals(
        "Invalid parameter", ((ApiError) actualHandleTypeMismatchResult.getBody()).getTitle());
    assertEquals(102, ((ApiError) actualHandleTypeMismatchResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getUserMessage());
    assertEquals(
        "The URL parameter '0123456789ABCDEF' received the value 'Value', which is of an invalid type. Correct"
            + " and try again.",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleTypeMismatchResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/invalid-parameter",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getType());
    verify(ex).getValue();
    verify(ex).getName();
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleTypeMismatch(TypeMismatchException,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleTypeMismatch7() {
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
    when(ex.getValue()).thenReturn("Value");
    when(ex.getName()).thenReturn("0123456789ABCDEF");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleTypeMismatchResult =
        apiExceptionHandler.handleTypeMismatch(
            ex,
            headers,
            HttpStatus.CHECKPOINT,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleTypeMismatchResult.hasBody());
    assertTrue(actualHandleTypeMismatchResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CHECKPOINT, actualHandleTypeMismatchResult.getStatusCode());
    assertEquals(
        "Invalid parameter", ((ApiError) actualHandleTypeMismatchResult.getBody()).getTitle());
    assertEquals(103, ((ApiError) actualHandleTypeMismatchResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getUserMessage());
    assertEquals(
        "The URL parameter '0123456789ABCDEF' received the value 'Value', which is of an invalid type. Correct"
            + " and try again.",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleTypeMismatchResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/invalid-parameter",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getType());
    verify(ex).getValue();
    verify(ex).getName();
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleTypeMismatch(TypeMismatchException,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleTypeMismatch8() {
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
    when(ex.getValue()).thenReturn("Value");
    when(ex.getName()).thenReturn("0123456789ABCDEF");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleTypeMismatchResult =
        apiExceptionHandler.handleTypeMismatch(
            ex, headers, HttpStatus.OK, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleTypeMismatchResult.hasBody());
    assertTrue(actualHandleTypeMismatchResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.OK, actualHandleTypeMismatchResult.getStatusCode());
    assertEquals(
        "Invalid parameter", ((ApiError) actualHandleTypeMismatchResult.getBody()).getTitle());
    assertEquals(200, ((ApiError) actualHandleTypeMismatchResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getUserMessage());
    assertEquals(
        "The URL parameter '0123456789ABCDEF' received the value 'Value', which is of an invalid type. Correct"
            + " and try again.",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleTypeMismatchResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/invalid-parameter",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getType());
    verify(ex).getValue();
    verify(ex).getName();
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleTypeMismatch(TypeMismatchException,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleTypeMismatch10() {
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
    when(ex.getValue()).thenReturn("Value");
    when(ex.getName()).thenReturn("0123456789ABCDEF");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleTypeMismatchResult =
        apiExceptionHandler.handleTypeMismatch(
            ex, headers, HttpStatus.CREATED, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleTypeMismatchResult.hasBody());
    assertTrue(actualHandleTypeMismatchResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CREATED, actualHandleTypeMismatchResult.getStatusCode());
    assertEquals(
        "Invalid parameter", ((ApiError) actualHandleTypeMismatchResult.getBody()).getTitle());
    assertEquals(201, ((ApiError) actualHandleTypeMismatchResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getUserMessage());
    assertEquals(
        "The URL parameter '0123456789ABCDEF' received the value 'Value', which is of an invalid type. Correct"
            + " and try again.",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleTypeMismatchResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/invalid-parameter",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getType());
    verify(ex).getValue();
    verify(ex).getName();
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleTypeMismatch(TypeMismatchException,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleTypeMismatch11() {
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
    when(ex.getValue()).thenReturn("Value");
    when(ex.getName()).thenReturn("0123456789ABCDEF");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleTypeMismatchResult =
        apiExceptionHandler.handleTypeMismatch(
            ex, headers, HttpStatus.ACCEPTED, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleTypeMismatchResult.hasBody());
    assertTrue(actualHandleTypeMismatchResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.ACCEPTED, actualHandleTypeMismatchResult.getStatusCode());
    assertEquals(
        "Invalid parameter", ((ApiError) actualHandleTypeMismatchResult.getBody()).getTitle());
    assertEquals(202, ((ApiError) actualHandleTypeMismatchResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getUserMessage());
    assertEquals(
        "The URL parameter '0123456789ABCDEF' received the value 'Value', which is of an invalid type. Correct"
            + " and try again.",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleTypeMismatchResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/invalid-parameter",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getType());
    verify(ex).getValue();
    verify(ex).getName();
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleTypeMismatch(TypeMismatchException,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleTypeMismatch12() {
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
    when(ex.getValue()).thenReturn("Value");
    when(ex.getName()).thenReturn("0123456789ABCDEF");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleTypeMismatchResult =
        apiExceptionHandler.handleTypeMismatch(
            ex,
            headers,
            HttpStatus.NON_AUTHORITATIVE_INFORMATION,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleTypeMismatchResult.hasBody());
    assertTrue(actualHandleTypeMismatchResult.getHeaders().isEmpty());
    assertEquals(
        HttpStatus.NON_AUTHORITATIVE_INFORMATION, actualHandleTypeMismatchResult.getStatusCode());
    assertEquals(
        "Invalid parameter", ((ApiError) actualHandleTypeMismatchResult.getBody()).getTitle());
    assertEquals(203, ((ApiError) actualHandleTypeMismatchResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getUserMessage());
    assertEquals(
        "The URL parameter '0123456789ABCDEF' received the value 'Value', which is of an invalid type. Correct"
            + " and try again.",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleTypeMismatchResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/invalid-parameter",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getType());
    verify(ex).getValue();
    verify(ex).getName();
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleTypeMismatch(TypeMismatchException,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleTypeMismatch13() {
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
    when(ex.getValue()).thenReturn("Value");
    when(ex.getName()).thenReturn("0123456789ABCDEF");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleTypeMismatchResult =
        apiExceptionHandler.handleTypeMismatch(
            ex,
            headers,
            HttpStatus.NO_CONTENT,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleTypeMismatchResult.hasBody());
    assertTrue(actualHandleTypeMismatchResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.NO_CONTENT, actualHandleTypeMismatchResult.getStatusCode());
    assertEquals(
        "Invalid parameter", ((ApiError) actualHandleTypeMismatchResult.getBody()).getTitle());
    assertEquals(204, ((ApiError) actualHandleTypeMismatchResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getUserMessage());
    assertEquals(
        "The URL parameter '0123456789ABCDEF' received the value 'Value', which is of an invalid type. Correct"
            + " and try again.",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleTypeMismatchResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/invalid-parameter",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getType());
    verify(ex).getValue();
    verify(ex).getName();
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleTypeMismatch(TypeMismatchException,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleTypeMismatch14() {
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
    when(ex.getValue()).thenReturn("Value");
    when(ex.getName()).thenReturn("0123456789ABCDEF");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleTypeMismatchResult =
        apiExceptionHandler.handleTypeMismatch(
            ex,
            headers,
            HttpStatus.RESET_CONTENT,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleTypeMismatchResult.hasBody());
    assertTrue(actualHandleTypeMismatchResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.RESET_CONTENT, actualHandleTypeMismatchResult.getStatusCode());
    assertEquals(
        "Invalid parameter", ((ApiError) actualHandleTypeMismatchResult.getBody()).getTitle());
    assertEquals(205, ((ApiError) actualHandleTypeMismatchResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getUserMessage());
    assertEquals(
        "The URL parameter '0123456789ABCDEF' received the value 'Value', which is of an invalid type. Correct"
            + " and try again.",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleTypeMismatchResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/invalid-parameter",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getType());
    verify(ex).getValue();
    verify(ex).getName();
  }

  /**
   * Method under test: {@link ApiExceptionHandler#handleTypeMismatch(TypeMismatchException,
   * HttpHeaders, HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleTypeMismatch15() {
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
    when(ex.getValue()).thenReturn("Value");
    when(ex.getName()).thenReturn("0123456789ABCDEF");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleTypeMismatchResult =
        apiExceptionHandler.handleTypeMismatch(
            ex,
            headers,
            HttpStatus.PARTIAL_CONTENT,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleTypeMismatchResult.hasBody());
    assertTrue(actualHandleTypeMismatchResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.PARTIAL_CONTENT, actualHandleTypeMismatchResult.getStatusCode());
    assertEquals(
        "Invalid parameter", ((ApiError) actualHandleTypeMismatchResult.getBody()).getTitle());
    assertEquals(206, ((ApiError) actualHandleTypeMismatchResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getUserMessage());
    assertEquals(
        "The URL parameter '0123456789ABCDEF' received the value 'Value', which is of an invalid type. Correct"
            + " and try again.",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleTypeMismatchResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/invalid-parameter",
        ((ApiError) actualHandleTypeMismatchResult.getBody()).getType());
    verify(ex).getValue();
    verify(ex).getName();
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleNoHandlerFoundException(NoHandlerFoundException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleNoHandlerFoundException() {
    NoHandlerFoundException ex =
        new NoHandlerFoundException(
            "https://example.org/example", "https://example.org/example", new HttpHeaders());

    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleNoHandlerFoundExceptionResult =
        apiExceptionHandler.handleNoHandlerFoundException(
            ex, headers, HttpStatus.CONTINUE, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleNoHandlerFoundExceptionResult.hasBody());
    assertTrue(actualHandleNoHandlerFoundExceptionResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONTINUE, actualHandleNoHandlerFoundExceptionResult.getStatusCode());
    assertEquals(
        "Resource not found",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getTitle());
    assertEquals(
        100,
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getUserMessage());
    assertEquals(
        "The resource 'https://example.org/example' do not exists. Correct and try again.",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/resource-not-found",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getType());
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleNoHandlerFoundException(NoHandlerFoundException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleNoHandlerFoundException3() {
    NoHandlerFoundException ex = mock(NoHandlerFoundException.class);
    when(ex.getRequestURL()).thenReturn("https://example.org/example");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleNoHandlerFoundExceptionResult =
        apiExceptionHandler.handleNoHandlerFoundException(
            ex, headers, HttpStatus.CONTINUE, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleNoHandlerFoundExceptionResult.hasBody());
    assertTrue(actualHandleNoHandlerFoundExceptionResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONTINUE, actualHandleNoHandlerFoundExceptionResult.getStatusCode());
    assertEquals(
        "Resource not found",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getTitle());
    assertEquals(
        100,
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getUserMessage());
    assertEquals(
        "The resource 'https://example.org/example' do not exists. Correct and try again.",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/resource-not-found",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getType());
    verify(ex).getRequestURL();
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleNoHandlerFoundException(NoHandlerFoundException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleNoHandlerFoundException4() {
    NoHandlerFoundException ex = mock(NoHandlerFoundException.class);
    when(ex.getRequestURL()).thenReturn("https://example.org/example");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleNoHandlerFoundExceptionResult =
        apiExceptionHandler.handleNoHandlerFoundException(
            ex,
            headers,
            HttpStatus.SWITCHING_PROTOCOLS,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleNoHandlerFoundExceptionResult.hasBody());
    assertTrue(actualHandleNoHandlerFoundExceptionResult.getHeaders().isEmpty());
    assertEquals(
        HttpStatus.SWITCHING_PROTOCOLS, actualHandleNoHandlerFoundExceptionResult.getStatusCode());
    assertEquals(
        "Resource not found",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getTitle());
    assertEquals(
        101,
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getUserMessage());
    assertEquals(
        "The resource 'https://example.org/example' do not exists. Correct and try again.",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/resource-not-found",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getType());
    verify(ex).getRequestURL();
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleNoHandlerFoundException(NoHandlerFoundException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleNoHandlerFoundException5() {
    NoHandlerFoundException ex = mock(NoHandlerFoundException.class);
    when(ex.getRequestURL()).thenReturn("https://example.org/example");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleNoHandlerFoundExceptionResult =
        apiExceptionHandler.handleNoHandlerFoundException(
            ex,
            headers,
            HttpStatus.PROCESSING,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleNoHandlerFoundExceptionResult.hasBody());
    assertTrue(actualHandleNoHandlerFoundExceptionResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.PROCESSING, actualHandleNoHandlerFoundExceptionResult.getStatusCode());
    assertEquals(
        "Resource not found",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getTitle());
    assertEquals(
        102,
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getUserMessage());
    assertEquals(
        "The resource 'https://example.org/example' do not exists. Correct and try again.",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/resource-not-found",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getType());
    verify(ex).getRequestURL();
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleNoHandlerFoundException(NoHandlerFoundException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleNoHandlerFoundException6() {
    NoHandlerFoundException ex = mock(NoHandlerFoundException.class);
    when(ex.getRequestURL()).thenReturn("https://example.org/example");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleNoHandlerFoundExceptionResult =
        apiExceptionHandler.handleNoHandlerFoundException(
            ex,
            headers,
            HttpStatus.CHECKPOINT,
            new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleNoHandlerFoundExceptionResult.hasBody());
    assertTrue(actualHandleNoHandlerFoundExceptionResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CHECKPOINT, actualHandleNoHandlerFoundExceptionResult.getStatusCode());
    assertEquals(
        "Resource not found",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getTitle());
    assertEquals(
        103,
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getUserMessage());
    assertEquals(
        "The resource 'https://example.org/example' do not exists. Correct and try again.",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/resource-not-found",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getType());
    verify(ex).getRequestURL();
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleNoHandlerFoundException(NoHandlerFoundException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleNoHandlerFoundException7() {
    NoHandlerFoundException ex = mock(NoHandlerFoundException.class);
    when(ex.getRequestURL()).thenReturn("https://example.org/example");
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleNoHandlerFoundExceptionResult =
        apiExceptionHandler.handleNoHandlerFoundException(
            ex, headers, HttpStatus.OK, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleNoHandlerFoundExceptionResult.hasBody());
    assertTrue(actualHandleNoHandlerFoundExceptionResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.OK, actualHandleNoHandlerFoundExceptionResult.getStatusCode());
    assertEquals(
        "Resource not found",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getTitle());
    assertEquals(
        200,
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getUserMessage());
    assertEquals(
        "The resource 'https://example.org/example' do not exists. Correct and try again.",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/resource-not-found",
        ((ApiError) actualHandleNoHandlerFoundExceptionResult.getBody()).getType());
    verify(ex).getRequestURL();
  }

  /** Method under test: {@link ApiExceptionHandler#handleUncaught(Exception, WebRequest)} */
  @Test
  void testHandleUncaught() {
    Exception ex = new Exception("An error occurred");
    ResponseEntity<Object> actualHandleUncaughtResult =
        apiExceptionHandler.handleUncaught(ex, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleUncaughtResult.hasBody());
    assertTrue(actualHandleUncaughtResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleUncaughtResult.getStatusCode());
    assertEquals("System error", ((ApiError) actualHandleUncaughtResult.getBody()).getTitle());
    assertEquals(500, ((ApiError) actualHandleUncaughtResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleUncaughtResult.getBody()).getUserMessage());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleUncaughtResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleUncaughtResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/system-error",
        ((ApiError) actualHandleUncaughtResult.getBody()).getType());
  }

  /** Method under test: {@link ApiExceptionHandler#handleUncaught(Exception, WebRequest)} */
  @Test
  void testHandleUncaught4() {
    Exception ex = new Exception("An error occurred");
    WebRequest request = mock(WebRequest.class);
    doNothing().when(request).setAttribute(Mockito.any(), Mockito.any(), anyInt());
    ResponseEntity<Object> actualHandleUncaughtResult =
        apiExceptionHandler.handleUncaught(ex, request);
    assertTrue(actualHandleUncaughtResult.hasBody());
    assertTrue(actualHandleUncaughtResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleUncaughtResult.getStatusCode());
    assertEquals("System error", ((ApiError) actualHandleUncaughtResult.getBody()).getTitle());
    assertEquals(500, ((ApiError) actualHandleUncaughtResult.getBody()).getStatus().intValue());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleUncaughtResult.getBody()).getUserMessage());
    assertEquals(
        ApiExceptionHandler.USER_MESSAGE,
        ((ApiError) actualHandleUncaughtResult.getBody()).getDetail());
    assertNull(((ApiError) actualHandleUncaughtResult.getBody()).getFields());
    assertEquals(
        "https://www.flashfood.com.br/system-error",
        ((ApiError) actualHandleUncaughtResult.getBody()).getType());
    verify(request).setAttribute(Mockito.any(), Mockito.any(), anyInt());
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleMethodArgumentNotValid(MethodArgumentNotValidException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleMethodArgumentNotValid2() {
    MethodArgumentNotValidException ex =
        new MethodArgumentNotValidException(null, new BindException("Target", "Object Name"));

    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleMethodArgumentNotValidResult =
        apiExceptionHandler.handleMethodArgumentNotValid(
            ex, headers, HttpStatus.CONTINUE, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleMethodArgumentNotValidResult.hasBody());
    assertTrue(actualHandleMethodArgumentNotValidResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONTINUE, actualHandleMethodArgumentNotValidResult.getStatusCode());
    assertEquals(
        "Invalid data", ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getTitle());
    assertEquals(
        100,
        ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getStatus().intValue());
    assertEquals(
        "One or more fields are invalid. Fill it correctly and try again.",
        ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getUserMessage());
    assertEquals(
        "One or more fields are invalid. Fill it correctly and try again.",
        ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getDetail());
    assertTrue(
        ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getFields().isEmpty());
    assertEquals(
        "https://www.flashfood.com.br/invalid-data",
        ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getType());
  }

  /**
   * Method under test: {@link
   * ApiExceptionHandler#handleMethodArgumentNotValid(MethodArgumentNotValidException, HttpHeaders,
   * HttpStatusCode, WebRequest)}
   */
  @Test
  void testHandleMethodArgumentNotValid3() {
    MethodParameter parameter = mock(MethodParameter.class);
    MethodArgumentNotValidException ex =
        new MethodArgumentNotValidException(parameter, new BindException("Target", "Object Name"));

    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> actualHandleMethodArgumentNotValidResult =
        apiExceptionHandler.handleMethodArgumentNotValid(
            ex, headers, HttpStatus.CONTINUE, new ServletWebRequest(new MockHttpServletRequest()));
    assertTrue(actualHandleMethodArgumentNotValidResult.hasBody());
    assertTrue(actualHandleMethodArgumentNotValidResult.getHeaders().isEmpty());
    assertEquals(HttpStatus.CONTINUE, actualHandleMethodArgumentNotValidResult.getStatusCode());
    assertEquals(
        "Invalid data", ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getTitle());
    assertEquals(
        100,
        ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getStatus().intValue());
    assertEquals(
        "One or more fields are invalid. Fill it correctly and try again.",
        ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getUserMessage());
    assertEquals(
        "One or more fields are invalid. Fill it correctly and try again.",
        ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getDetail());
    assertTrue(
        ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getFields().isEmpty());
    assertEquals(
        "https://www.flashfood.com.br/invalid-data",
        ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getType());
  }
}
