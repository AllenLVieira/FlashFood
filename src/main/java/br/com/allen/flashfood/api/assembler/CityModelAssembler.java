package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.response.CityResponse;
import br.com.allen.flashfood.domain.model.City;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityModelAssembler {
  @Autowired private ModelMapper modelMapper;

  public CityResponse toModel(City city) {
    return modelMapper.map(city, CityResponse.class);
  }

  public List<CityResponse> toCollectionModel(List<City> cities) {
    return cities.stream().map(city -> toModel(city)).collect(Collectors.toList());
  }
}
