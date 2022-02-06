package br.com.allen.flashfood.api.exceptionhandler;

import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(EntityNotFoundedException.class)
    public ResponseEntity<?> handleEntityNotFoundedException(EntityNotFoundedException e){
        ApiError errors = ApiError.builder()
                .datetime(LocalDateTime.now())
                .message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errors);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException e){
        ApiError errors = ApiError.builder()
                .datetime(LocalDateTime.now())
                .message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errors);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e){
        ApiError errors = ApiError.builder()
                .datetime(LocalDateTime.now())
                .message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}
