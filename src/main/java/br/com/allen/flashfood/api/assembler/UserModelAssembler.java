package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.controller.UserController;
import br.com.allen.flashfood.api.controller.UserGroupController;
import br.com.allen.flashfood.api.model.response.UserResponse;
import br.com.allen.flashfood.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserResponse> {
  private final ModelMapper modelMapper;

  public UserModelAssembler(ModelMapper modelMapper) {
    super(UserController.class, UserResponse.class);
    this.modelMapper = modelMapper;
  }

  @Override
  public UserResponse toModel(User user) {
    UserResponse response = createModelWithId(user.getId(), user);
    modelMapper.map(user, response);

    response.add(WebMvcLinkBuilder.linkTo(UserModelAssembler.class).withRel("users"));
    response.add(
        (WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserGroupController.class).getAllGroups(user.getId())))
            .withRel("users-groups"));
    return response;
  }

  @Override
  public CollectionModel<UserResponse> toCollectionModel(Iterable<? extends User> entities) {
    return super.toCollectionModel(entities)
        .add(WebMvcLinkBuilder.linkTo(UserController.class).withSelfRel());
  }
}
