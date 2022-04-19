package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.request.CuisineRequest;
import br.com.allen.flashfood.domain.model.Cuisine;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CuisineRequestDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cuisine toDomainObject(CuisineRequest cuisineRequest) {
        return modelMapper.map(cuisineRequest, Cuisine.class);
    }

    public void copyToDomainObject(CuisineRequest cuisineRequest, Cuisine cuisine) {
        modelMapper.map(cuisineRequest, cuisine);
    }
}
