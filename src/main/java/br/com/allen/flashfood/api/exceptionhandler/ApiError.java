package br.com.allen.flashfood.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiError {
    private LocalDateTime datetime;
    private String message;
}
