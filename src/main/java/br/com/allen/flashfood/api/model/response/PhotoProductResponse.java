package br.com.allen.flashfood.api.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhotoProductResponse {
  private String filename;
  private String description;
  private String contentType;
  private Long filesize;
}
