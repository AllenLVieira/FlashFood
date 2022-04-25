package br.com.allen.flashfood.api.assembler;

import br.com.allen.flashfood.api.model.request.UserRequest;
import br.com.allen.flashfood.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRequestDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public User toDomainObject(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    public void copyToDomainObject(UserRequest userRequest, User user) {
        modelMapper.map(userRequest, user);
    }
}
