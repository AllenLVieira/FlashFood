package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.request.FamilyRequest;
import br.com.allen.flashfood.domain.model.Family;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FamilyRequestDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Family toDomainObject(FamilyRequest groupRequest) {
        return modelMapper.map(groupRequest, Family.class);
    }

    public void copyToDomainObject(FamilyRequest groupRequest, Family group) {
        modelMapper.map(groupRequest, group);
    }
}
