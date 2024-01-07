package br.com.allen.flashfood.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "cities")
public class CityResponse extends RepresentationModel<CityResponse> {
  @Schema(example = "1")
  private Long id;

  @Schema(example = "Porto Velho")
  private String name;

  private StateResponse state;
}
