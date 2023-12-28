package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.request.ProductRequest;
import br.com.allen.flashfood.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestDisassembler {
  @Autowired private ModelMapper modelMapper;

  public Product toDomainObject(ProductRequest productRequest) {
    return modelMapper.map(productRequest, Product.class);
  }

  public void copyToDomainObject(ProductRequest productRequest, Product product) {
    modelMapper.map(productRequest, product);
  }
}
