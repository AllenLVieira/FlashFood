package br.com.allen.flashfood.api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductPhotoRequest {
  @NotNull private MultipartFile file;
  @NotBlank private String description;
}
