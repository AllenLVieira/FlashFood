package br.com.allen.flashfood.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "users")
public class UserResponse extends RepresentationModel<UserResponse> {
  @Schema(example = "1")
  private Long id;

  @Schema(example = "Allen Vieira")
  private String name;

  @Schema(example = "allenvieira96@gmail.com")
  private String email;
}
