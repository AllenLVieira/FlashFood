package br.com.allen.flashfood.api.exceptionhandler;

import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundedException.class)
    public ResponseEntity<?> handleEntityNotFoundedException(EntityNotFoundedException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorsType type = ErrorsType.ENTITY_NOT_FOUND;
        String detail = ex.getMessage();
        ApiError errors = apiErrorBuilder(status, type, detail).build();
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorsType type = ErrorsType.ENTITY_IN_USE;
        String detail = ex.getMessage();
        ApiError errors = apiErrorBuilder(status, type, detail).build();
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorsType type = ErrorsType.BUSINESS_ERROR;
        String detail = ex.getMessage();
        ApiError errors = apiErrorBuilder(status, type, detail).build();
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            body = ApiError.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = ApiError.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ApiError.ApiErrorBuilder apiErrorBuilder(HttpStatus status,
                                                     ErrorsType errorsType,
                                                     String detail) {
        return ApiError.builder()
                .status(status.value())
                .type(errorsType.getUri())
                .title(errorsType.getTitle())
                .detail(detail);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }
        ErrorsType type = ErrorsType.MESSAGE_NOT_READABLE;
        String detail = "The body of the request is invalid. Please check for syntax errors.";
        ApiError errors = apiErrorBuilder(status, type, detail).build();
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());
        ErrorsType type = ErrorsType.MESSAGE_NOT_READABLE;
        String detail = String.format("Property '%s' do not exists. Fix it or remove it and try again.", path);
        ApiError errors = apiErrorBuilder(status, type, detail).build();
        return handleExceptionInternal(ex, errors, headers, status, request);
    }

    private String joinPath(List<JsonMappingException.Reference> path) {
        return path.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers,
                                                                    HttpStatus status, WebRequest request) {
        ErrorsType type = ErrorsType.INVALID_PARAMETER;
        String detail = String.format("The URL parameter '%s' received the value '%s', " +
                "which is of an invalid type. Correct and try again.", ex.getName(), ex.getValue());
        ApiError body = apiErrorBuilder(status, type, detail).build();
        return handleExceptionInternal(ex, body, headers, status, request);
    }
}
