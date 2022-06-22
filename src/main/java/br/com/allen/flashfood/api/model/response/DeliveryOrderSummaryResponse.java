package br.com.allen.flashfood.api.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class DeliveryOrderSummaryResponse {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal freightRate;
    private BigDecimal amount;
    private String status;
    private OffsetDateTime registrationDate;
    private RestaurantSummaryResponse restaurant;
    private UserResponse user;
}
