package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.response.PhotoProductResponse;
import br.com.allen.flashfood.domain.model.PhotoProduct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhotoProductModelAssembler {
  private final ModelMapper modelMapper;

  public PhotoProductResponse toModel(PhotoProduct photo) {
    return modelMapper.map(photo, PhotoProductResponse.class);
  }
}
