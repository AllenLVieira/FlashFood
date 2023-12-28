package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.response.FamilyResponse;
import br.com.allen.flashfood.domain.model.Family;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FamilyModelAssembler {
  @Autowired private ModelMapper modelMapper;

  public FamilyResponse toModel(Family group) {
    return modelMapper.map(group, FamilyResponse.class);
  }

  public List<FamilyResponse> toCollectionModel(Collection<Family> groups) {
    return groups.stream().map(group -> toModel(group)).collect(Collectors.toList());
  }
}
