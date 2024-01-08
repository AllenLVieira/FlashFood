package br.com.allen.flashfood.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class DeliveryOrderResponse extends RepresentationModel<DeliveryOrderResponse> {

  @Schema(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
  private String orderCode;

  @Schema(example = "300.0")
  private BigDecimal subtotal;

  @Schema(example = "10.0")
  private BigDecimal freightRate;

  @Schema(example = "310.0")
  private BigDecimal amount;

  @Schema(example = "CREATED")
  private String status;

  @Schema(example = "2024-01-01T20:34:04Z")
  private OffsetDateTime registrationDate;

  @Schema(example = "2024-01-01T20:36:04Z")
  private OffsetDateTime confirmationDate;

  @Schema(example = "2024-01-01T20:54:04Z")
  private OffsetDateTime deliveryDate;

  @Schema(example = "2024-01-01T20:34:04Z")
  private OffsetDateTime cancellationDate;

  private RestaurantSummaryResponse restaurant;
  private UserResponse user;
  private PaymentMethodResponse paymentMethod;
  private AddressResponse deliveryAddress;
  private List<OrderItemResponse> items;
}
