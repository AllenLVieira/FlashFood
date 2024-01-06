package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.UserModelAssembler;
import br.com.allen.flashfood.api.assembler.UserRequestDisassembler;
import br.com.allen.flashfood.api.model.request.PasswordRequest;
import br.com.allen.flashfood.api.model.request.UserPasswordRequest;
import br.com.allen.flashfood.api.model.request.UserRequest;
import br.com.allen.flashfood.api.model.response.UserResponse;
import br.com.allen.flashfood.domain.model.User;
import br.com.allen.flashfood.domain.repository.UserRepository;
import br.com.allen.flashfood.domain.service.UserRegistrationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(
    name = "User",
    description =
        "Responsible for managing user accounts in the FlashFood application."
            + " This controller handles creating new user accounts, updating user information, managing password"
            + " changes, and deleting user accounts when necessary. It is crucial for maintaining user data integrity,"
            + " security, and ensuring a seamless user experience across the application.")
public class UserController {
  private final UserRepository userRepository;
  private final UserRegistrationsService userRegistrationsService;
  private final UserModelAssembler userModelAssembler;
  private final UserRequestDisassembler userRequestDisassembler;

  @GetMapping
  @Operation(description = "Get all the users in the Flashfood application.")
  public List<UserResponse> getAllUsers() {
    List<User> allUsers = userRepository.findAll();
    return userModelAssembler.toCollectionModel(allUsers);
  }

  @GetMapping("/{userId}")
  @Operation(description = "Get a user by Id in the Flashfood application.")
  public UserResponse getUserById(@PathVariable Long userId) {
    User user = userRegistrationsService.findUserOrElseThrow(userId);
    return userModelAssembler.toModel(user);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(description = "Create new user in the Flashfood application.")
  public UserResponse addUser(@RequestBody @Valid UserPasswordRequest userPasswordRequest) {
    User user = userRequestDisassembler.toDomainObject(userPasswordRequest);
    user = userRegistrationsService.saveUser(user);
    return userModelAssembler.toModel(user);
  }

  @PutMapping("/{userId}")
  @Operation(description = "Update user by Id in the Flashfood application.")
  public UserResponse updateUser(
      @PathVariable Long userId, @RequestBody @Valid UserRequest userRequest) {
    User actualUser = userRegistrationsService.findUserOrElseThrow(userId);
    userRequestDisassembler.copyToDomainObject(userRequest, actualUser);
    actualUser = userRegistrationsService.saveUser(actualUser);
    return userModelAssembler.toModel(actualUser);
  }

  @PutMapping("/{userId}/password")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Change user password in the Flashfood application.")
  public void userPassword(
      @PathVariable Long userId, @RequestBody @Valid PasswordRequest passwordRequest) {
    userRegistrationsService.changePassword(
        userId, passwordRequest.getActualPassword(), passwordRequest.getNewPassword());
  }
}
