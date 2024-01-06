package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.FamilyModelAssembler;
import br.com.allen.flashfood.api.model.response.FamilyResponse;
import br.com.allen.flashfood.domain.model.User;
import br.com.allen.flashfood.domain.service.UserRegistrationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users/{userId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(
    name = "User Group",
    description =
        "Manages user groups within the FlashFood application. This"
            + " controller is responsible for creating and managing user groups, adding users to these groups,"
            + " updating group information, and handling the removal of groups or users from groups as needed."
            + " It plays a crucial role in facilitating group-based functionalities and permissions, enhancing"
            + " collaborative features and user management.")
public class UserGroupController {
  private final UserRegistrationsService userService;
  private final FamilyModelAssembler groupAssembler;

  @GetMapping
  @Operation(description = "Get all the groups in the Flashfood application.")
  public List<FamilyResponse> getAllGroups(@PathVariable Long userId) {
    User user = userService.findUserOrElseThrow(userId);
    return groupAssembler.toCollectionModel(user.getGroups());
  }

  @DeleteMapping("/{groupId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Unlink user from group in the Flashfood application.")
  public void unlinkGroup(@PathVariable Long userId, @PathVariable Long groupId) {
    userService.unlinkGroup(userId, groupId);
  }

  @PutMapping("/{groupId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Link an userId to a group in the Flashfood application.")
  public void linkGroup(@PathVariable Long userId, @PathVariable Long groupId) {
    userService.linkGroup(userId, groupId);
  }
}
