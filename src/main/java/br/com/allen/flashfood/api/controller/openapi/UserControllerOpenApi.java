package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.model.request.PasswordRequest;
import br.com.allen.flashfood.api.model.request.UserPasswordRequest;
import br.com.allen.flashfood.api.model.request.UserRequest;
import br.com.allen.flashfood.api.model.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(
    name = "User",
    description =
        "Responsible for managing user accounts in the FlashFood application."
            + " This controller handles creating new user accounts, updating user information, managing password"
            + " changes, and deleting user accounts when necessary. It is crucial for maintaining user data integrity,"
            + " security, and ensuring a seamless user experience across the application.")
public interface UserControllerOpenApi {
  @Operation(description = "Get all the users in the Flashfood application.")
  CollectionModel<UserResponse> getAllUsers();

  @Operation(description = "Get a user by Id in the Flashfood application.")
  UserResponse getUserById(Long userId);

  @Operation(description = "Create new user in the Flashfood application.")
  UserResponse addUser(UserPasswordRequest userPasswordRequest);

  @Operation(description = "Update user by Id in the Flashfood application.")
  UserResponse updateUser(Long userId, UserRequest userRequest);

  @Operation(description = "Change user password in the Flashfood application.")
  void userPassword(Long userId, PasswordRequest passwordRequest);
}
