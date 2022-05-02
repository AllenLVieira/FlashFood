package br.com.allen.flashfood.core.config.modelmapper;

import br.com.allen.flashfood.api.model.response.AddressResponse;
import br.com.allen.flashfood.domain.model.Address;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        var addressToAddressResponseTypeMap = modelMapper.createTypeMap(
                Address.class, AddressResponse.class);
        addressToAddressResponseTypeMap.<String>addMapping(source -> source.getCity().getState().getName(),
                (target, value) -> target.getCity().setState(value));
        return modelMapper;
    }
}
