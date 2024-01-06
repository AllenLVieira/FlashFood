package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.UserModelAssembler;
import br.com.allen.flashfood.api.assembler.UserRequestDisassembler;
import br.com.allen.flashfood.api.controller.openapi.UserControllerOpenApi;
import br.com.allen.flashfood.api.model.request.PasswordRequest;
import br.com.allen.flashfood.api.model.request.UserPasswordRequest;
import br.com.allen.flashfood.api.model.request.UserRequest;
import br.com.allen.flashfood.api.model.response.UserResponse;
import br.com.allen.flashfood.domain.model.User;
import br.com.allen.flashfood.domain.repository.UserRepository;
import br.com.allen.flashfood.domain.service.UserRegistrationsService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController implements UserControllerOpenApi {
  private final UserRepository userRepository;
  private final UserRegistrationsService userRegistrationsService;
  private final UserModelAssembler userModelAssembler;
  private final UserRequestDisassembler userRequestDisassembler;

  @GetMapping
  public List<UserResponse> getAllUsers() {
    List<User> allUsers = userRepository.findAll();
    return userModelAssembler.toCollectionModel(allUsers);
  }

  @GetMapping("/{userId}")
  public UserResponse getUserById(@PathVariable Long userId) {
    User user = userRegistrationsService.findUserOrElseThrow(userId);
    return userModelAssembler.toModel(user);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse addUser(@RequestBody @Valid UserPasswordRequest userPasswordRequest) {
    User user = userRequestDisassembler.toDomainObject(userPasswordRequest);
    user = userRegistrationsService.saveUser(user);
    return userModelAssembler.toModel(user);
  }

  @PutMapping("/{userId}")
  public UserResponse updateUser(
      @PathVariable Long userId, @RequestBody @Valid UserRequest userRequest) {
    User actualUser = userRegistrationsService.findUserOrElseThrow(userId);
    userRequestDisassembler.copyToDomainObject(userRequest, actualUser);
    actualUser = userRegistrationsService.saveUser(actualUser);
    return userModelAssembler.toModel(actualUser);
  }

  @PutMapping("/{userId}/password")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void userPassword(
      @PathVariable Long userId, @RequestBody @Valid PasswordRequest passwordRequest) {
    userRegistrationsService.changePassword(
        userId, passwordRequest.getActualPassword(), passwordRequest.getNewPassword());
  }
}
