package br.com.allen.flashfood.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhotoProductResponse {
  @Schema(example = "b8bbd21a-4dd3-4954-835c-3493af2ba6a0_Potato-Soup.jpg")
  private String filename;

  @Schema(example = "Photo of a potato soup")
  private String description;

  @Schema(example = "image/jpeg")
  private String contentType;

  @Schema(example = "203002")
  private Long filesize;
}
