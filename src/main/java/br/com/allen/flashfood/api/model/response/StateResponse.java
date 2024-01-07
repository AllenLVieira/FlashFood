package br.com.allen.flashfood.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class StateResponse extends RepresentationModel<StateResponse> {

  @Schema(example = "1")
  private Long id;

  @Schema(example = "Rondônia")
  private String name;
}
