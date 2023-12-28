package br.com.allen.flashfood.core.config.modelmapper;

import br.com.allen.flashfood.api.model.request.OrderItemRequest;
import br.com.allen.flashfood.api.model.response.AddressResponse;
import br.com.allen.flashfood.domain.model.Address;
import br.com.allen.flashfood.domain.model.OrderItem;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    var modelMapper = new ModelMapper();
    var addressToAddressResponseTypeMap =
        modelMapper.createTypeMap(Address.class, AddressResponse.class);
    addressToAddressResponseTypeMap.<String>addMapping(
        source -> source.getCity().getState().getName(),
        (target, value) -> target.getCity().setState(value));
    modelMapper
        .createTypeMap(OrderItemRequest.class, OrderItem.class)
        .addMappings(mapper -> mapper.skip(OrderItem::setId));
    return modelMapper;
  }
}
