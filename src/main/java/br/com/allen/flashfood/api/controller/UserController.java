package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.UserModelAssembler;
import br.com.allen.flashfood.api.assembler.UserRequestDisassembler;
import br.com.allen.flashfood.api.model.request.PasswordRequest;
import br.com.allen.flashfood.api.model.request.UserPasswordRequest;
import br.com.allen.flashfood.api.model.request.UserRequest;
import br.com.allen.flashfood.api.model.response.UserResponse;
import br.com.allen.flashfood.domain.model.User;
import br.com.allen.flashfood.domain.repository.UserRepository;
import br.com.allen.flashfood.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Autowired
    private UserRequestDisassembler userRequestDisassembler;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return userModelAssembler.toCollectionModel(allUsers);
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Long userId) {
        User user = userService.findUserOrElseThrow(userId);
        return userModelAssembler.toModel(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse addUser(@RequestBody @Valid UserPasswordRequest userPasswordRequest) {
        User user = userRequestDisassembler.toDomainObject(userPasswordRequest);
        user = userService.saveUser(user);
        return userModelAssembler.toModel(user);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable Long userId,
                                   @RequestBody @Valid UserRequest userRequest) {
        User actualUser = userService.findUserOrElseThrow(userId);
        userRequestDisassembler.copyToDomainObject(userRequest, actualUser);
        actualUser = userService.saveUser(actualUser);
        return userModelAssembler.toModel(actualUser);
    }

    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void userPassword(@PathVariable Long userId,
                             @RequestBody @Valid PasswordRequest passwordRequest) {
        userService.changePassword(userId, passwordRequest.getActualPassword(), passwordRequest.getNewPassword());
    }
}
