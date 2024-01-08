package br.com.allen.flashfood.api.model.response;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@JsonFilter("orderFilter")
@Getter
@Setter
@Relation(collectionRelation = "orders")
public class DeliveryOrderSummaryResponse
    extends RepresentationModel<DeliveryOrderSummaryResponse> {

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

  private RestaurantSummaryResponse restaurant;
  private UserResponse user;
}
