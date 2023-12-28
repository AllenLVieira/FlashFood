package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.response.UserResponse;
import br.com.allen.flashfood.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserModelAssembler {
  @Autowired private ModelMapper modelMapper;

  public UserResponse toModel(User user) {
    return modelMapper.map(user, UserResponse.class);
  }

  public List<UserResponse> toCollectionModel(Collection<User> userList) {
    return userList.stream().map(user -> toModel(user)).collect(Collectors.toList());
  }
}
