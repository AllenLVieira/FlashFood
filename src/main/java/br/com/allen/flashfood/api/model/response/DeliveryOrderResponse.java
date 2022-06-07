package br.com.allen.flashfood.api.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class DeliveryOrderResponse {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal freightRate;
    private BigDecimal amount;
    private String status;
    private OffsetDateTime registrationDate;
    private OffsetDateTime confirmationDate;
    private OffsetDateTime deliveryDate;
    private OffsetDateTime cancellationDate;
    private RestaurantSummaryResponse restaurant;
    private UserResponse userResponse;
    private PaymentMethodResponse paymentMethod;
    private AddressResponse deliveryAddress;
    private List<OrderItemResponse> items;
}
