package br.com.allen.flashfood.api.model.response;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@JsonFilter("orderFilter")
@Getter
@Setter
public class DeliveryOrderSummaryResponse {

    private String orderCode;
    private BigDecimal subtotal;
    private BigDecimal freightRate;
    private BigDecimal amount;
    private String status;
    private OffsetDateTime registrationDate;
    private RestaurantSummaryResponse restaurant;
    private UserResponse user;
}
