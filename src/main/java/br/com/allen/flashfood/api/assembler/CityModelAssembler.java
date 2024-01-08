package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.controller.CityController;
import br.com.allen.flashfood.api.controller.StateController;
import br.com.allen.flashfood.api.model.response.CityResponse;
import br.com.allen.flashfood.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CityModelAssembler extends RepresentationModelAssemblerSupport<City, CityResponse> {
  private final ModelMapper modelMapper;

  public CityModelAssembler(ModelMapper modelMapper) {
    super(CityController.class, CityResponse.class);
    this.modelMapper = modelMapper;
  }

  @Override
  public CityResponse toModel(City city) {
    CityResponse response = createModelWithId(city.getId(), city);
    modelMapper.map(city, response);

    response.add(
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CityController.class).getAllCity())
            .withRel("cities"));
    response
        .getState()
        .add(
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(StateController.class)
                        .getStateById(response.getState().getId()))
                .withSelfRel());

    return response;
  }

  @Override
  public CollectionModel<CityResponse> toCollectionModel(Iterable<? extends City> entities) {
    return super.toCollectionModel(entities)
        .add(WebMvcLinkBuilder.linkTo(CityController.class).withSelfRel());
  }
}
