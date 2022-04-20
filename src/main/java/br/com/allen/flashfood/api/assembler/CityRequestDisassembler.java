package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.request.CityRequest;
import br.com.allen.flashfood.domain.model.City;
import br.com.allen.flashfood.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityRequestDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public City toDomainObject(CityRequest cityRequest) {
        return modelMapper.map(cityRequest, City.class);
    }

    public void copyToDomainObject(CityRequest cityRequest, City city) {
        // Ensure that a city's state can be updated.
        city.setState(new State());
        modelMapper.map(cityRequest, city);
    }
}
