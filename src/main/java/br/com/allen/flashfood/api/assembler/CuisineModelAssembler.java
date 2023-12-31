package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.response.CuisineResponse;
import br.com.allen.flashfood.domain.model.Cuisine;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CuisineModelAssembler {
  @Autowired private ModelMapper modelMapper;

  public CuisineResponse toModel(Cuisine cuisine) {
    return modelMapper.map(cuisine, CuisineResponse.class);
  }

  public List<CuisineResponse> toCollectionModel(List<Cuisine> cuisines) {
    return cuisines.stream().map(cuisine -> toModel(cuisine)).collect(Collectors.toList());
  }
}
