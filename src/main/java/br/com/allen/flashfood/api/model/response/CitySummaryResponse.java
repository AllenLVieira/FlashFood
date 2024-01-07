package br.com.allen.flashfood.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitySummaryResponse {

  @Schema(example = "1")
  private Long id;

  @Schema(example = "Porto Velho")
  private String name;

  @Schema(example = "Rondônia")
  private String state;
}
