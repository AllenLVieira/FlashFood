package br.com.allen.flashfood.api.exceptionhandler;

import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
        ErrorsType type = ErrorsType.MESSAGE_NOT_READABLE;
        String detail = "The body of the request is invalid. Please check for syntax errors.";
        ApiError errors = apiErrorBuilder(status, type, detail).build();
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }
}
