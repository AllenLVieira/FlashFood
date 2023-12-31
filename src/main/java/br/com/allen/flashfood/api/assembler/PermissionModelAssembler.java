package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.response.PermissionResponse;
import br.com.allen.flashfood.domain.model.Permission;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionModelAssembler {
  @Autowired private ModelMapper modelMapper;

  public PermissionResponse toModel(Permission permission) {
    return modelMapper.map(permission, PermissionResponse.class);
  }

  public List<PermissionResponse> toCollectionModel(Collection<Permission> permissionList) {
    return permissionList.stream()
        .map(permission -> toModel(permission))
        .collect(Collectors.toList());
  }
}
