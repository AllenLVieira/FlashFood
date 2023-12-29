package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.response.ProductResponse;
import br.com.allen.flashfood.domain.model.Product;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductModelAssembler {
  @Autowired private ModelMapper modelMapper;

  public ProductResponse toModel(Product product) {
    return modelMapper.map(product, ProductResponse.class);
  }

  public List<ProductResponse> toCollectionModel(List<Product> products) {
    return products.stream().map(product -> toModel(product)).collect(Collectors.toList());
  }
}
