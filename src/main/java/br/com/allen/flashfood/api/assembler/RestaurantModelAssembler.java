package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.response.RestaurantResponse;
import br.com.allen.flashfood.domain.model.Restaurant;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantModelAssembler {

  @Autowired private ModelMapper modelMapper;

  public RestaurantResponse toModel(Restaurant restaurant) {
    return modelMapper.map(restaurant, RestaurantResponse.class);
  }

  public List<RestaurantResponse> toCollectionModel(List<Restaurant> restaurants) {
    return restaurants.stream().map(restaurant -> toModel(restaurant)).collect(Collectors.toList());
  }
}
