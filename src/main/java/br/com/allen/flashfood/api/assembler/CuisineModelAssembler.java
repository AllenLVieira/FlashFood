package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.controller.CuisineController;
import br.com.allen.flashfood.api.model.response.CuisineResponse;
import br.com.allen.flashfood.domain.model.Cuisine;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CuisineModelAssembler
    extends RepresentationModelAssemblerSupport<Cuisine, CuisineResponse> {
  private final ModelMapper modelMapper;

  public CuisineModelAssembler(ModelMapper modelMapper) {
    super(CuisineController.class, CuisineResponse.class);
    this.modelMapper = modelMapper;
  }

  @Override
  public CuisineResponse toModel(Cuisine cuisine) {
    CuisineResponse response = createModelWithId(cuisine.getId(), cuisine);
    modelMapper.map(cuisine, response);

    response.add(WebMvcLinkBuilder.linkTo(CuisineController.class).withRel("cuisines"));
    return response;
  }

  @Override
  public CollectionModel<CuisineResponse> toCollectionModel(Iterable<? extends Cuisine> entities) {
    return super.toCollectionModel(entities);
  }
}
