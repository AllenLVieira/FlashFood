package br.com.allen.flashfood.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "paymentMethods")
public class PaymentMethodResponse extends RepresentationModel<PaymentMethodResponse> {
  @Schema(example = "1")
  private Long id;

  @Schema(example = "Credit card")
  private String description;
}
