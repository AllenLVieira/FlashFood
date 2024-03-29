package br.com.allen.flashfood.api.exceptionhandler;

import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  public static final String USER_MESSAGE =
      "An unexpected internal error has occurred in the system. "
          + "Try again and if the problem persists, contact your system administrator.";

  private final MessageSource msgSource;

  @ExceptionHandler(EntityNotFoundedException.class)
  public ResponseEntity<?> handleEntityNotFoundedException(
      EntityNotFoundedException ex, WebRequest request) {
    HttpStatusCode status = HttpStatus.NOT_FOUND;
    ErrorsType type = ErrorsType.RESOURCE_NOT_FOUND;
    String detail = ex.getMessage();
    ApiError errors = apiErrorBuilder(status, type, detail).build();
    return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
  }

  @ExceptionHandler(EntityInUseException.class)
  public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request) {
    HttpStatusCode status = HttpStatus.CONFLICT;
    ErrorsType type = ErrorsType.ENTITY_IN_USE;
    String detail = ex.getMessage();
    ApiError errors = apiErrorBuilder(status, type, detail).userMessage(detail).build();
    return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {
    HttpStatusCode status = HttpStatus.BAD_REQUEST;
    ErrorsType type = ErrorsType.BUSINESS_ERROR;
    String detail = ex.getMessage();
    ApiError errors = apiErrorBuilder(status, type, detail).userMessage(detail).build();
    return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    if (body == null) {
      HttpStatus httpStatus = HttpStatus.valueOf(status.value());
      String reasonPhrase = httpStatus.getReasonPhrase();

      body =
          ApiError.builder()
              .timestamp(OffsetDateTime.now())
              .title(reasonPhrase)
              .status(status.value())
              .userMessage(USER_MESSAGE)
              .build();
    } else if (body instanceof String) {
      body =
          ApiError.builder()
              .timestamp(OffsetDateTime.now())
              .title((String) body)
              .status(status.value())
              .userMessage(USER_MESSAGE)
              .build();
    }
    return super.handleExceptionInternal(ex, body, headers, status, request);
  }

  private ApiError.ApiErrorBuilder apiErrorBuilder(
      HttpStatusCode status, ErrorsType errorsType, String detail) {
    return ApiError.builder()
        .timestamp(OffsetDateTime.now())
        .status(status.value())
        .type(errorsType.getUri())
        .title(errorsType.getTitle())
        .detail(detail);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    Throwable rootCause = ExceptionUtils.getRootCause(ex);
    if (rootCause instanceof PropertyBindingException) {
      return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
    }
    ErrorsType type = ErrorsType.MESSAGE_NOT_READABLE;
    String detail = "The body of the request is invalid. Please check for syntax errors.";
    ApiError errors = apiErrorBuilder(status, type, detail).userMessage(USER_MESSAGE).build();
    return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
  }

  private ResponseEntity<Object> handlePropertyBinding(
      PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    String path = joinPath(ex.getPath());
    ErrorsType type = ErrorsType.MESSAGE_NOT_READABLE;
    String detail =
        String.format("Property '%s' do not exists. Fix it or remove it and try again.", path);
    ApiError errors = apiErrorBuilder(status, type, detail).userMessage(USER_MESSAGE).build();
    return handleExceptionInternal(ex, errors, headers, status, request);
  }

  private String joinPath(List<JsonMappingException.Reference> path) {
    return path.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    if (ex instanceof MethodArgumentTypeMismatchException) {
      return handleMethodArgumentTypeMismatch(
          (MethodArgumentTypeMismatchException) ex, headers, status, request);
    }
    return super.handleTypeMismatch(ex, headers, status, request);
  }

  private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    ErrorsType type = ErrorsType.INVALID_PARAMETER;
    String detail =
        String.format(
            "The URL parameter '%s' received the value '%s', "
                + "which is of an invalid type. Correct and try again.",
            ex.getName(), ex.getValue());
    ApiError body = apiErrorBuilder(status, type, detail).userMessage(USER_MESSAGE).build();
    return handleExceptionInternal(ex, body, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    ErrorsType type = ErrorsType.RESOURCE_NOT_FOUND;
    String detail =
        String.format(
            "The resource '%s' do not exists. Correct and try again.", ex.getRequestURL());
    ApiError body = apiErrorBuilder(status, type, detail).userMessage(USER_MESSAGE).build();
    return super.handleExceptionInternal(ex, body, headers, status, request);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
    HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
    ErrorsType type = ErrorsType.SYSTEM_ERROR;
    ex.printStackTrace();
    ApiError body = apiErrorBuilder(status, type, USER_MESSAGE).userMessage(USER_MESSAGE).build();
    return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
      HttpMediaTypeNotAcceptableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    return ResponseEntity.status(status).headers(headers).build();
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    return handleInternalValidation(ex, headers, status, request, ex.getBindingResult());
  }

  private ResponseEntity<Object> handleInternalValidation(
      Exception ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request,
      BindingResult bindingResult) {
    ErrorsType type = ErrorsType.INVALID_DATA;
    String detail = "One or more fields are invalid. Fill it correctly and try again.";

    List<ApiError.Field> fields =
        bindingResult.getFieldErrors().stream()
            .map(
                fieldError -> {
                  String message =
                      msgSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                  return ApiError.Field.builder()
                      .name(fieldError.getField())
                      .userMessage(message)
                      .build();
                })
            .collect(Collectors.toList());
    ApiError errors =
        apiErrorBuilder(status, type, detail).userMessage(detail).fields(fields).build();
    return super.handleExceptionInternal(ex, errors, headers, status, request);
  }
}
