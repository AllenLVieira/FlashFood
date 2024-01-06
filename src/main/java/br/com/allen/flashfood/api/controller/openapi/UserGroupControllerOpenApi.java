package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.model.response.FamilyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(
    name = "User Group",
    description =
        "Manages user groups within the FlashFood application. This"
            + " controller is responsible for creating and managing user groups, adding users to these groups,"
            + " updating group information, and handling the removal of groups or users from groups as needed."
            + " It plays a crucial role in facilitating group-based functionalities and permissions, enhancing"
            + " collaborative features and user management.")
public interface UserGroupControllerOpenApi {

  @Operation(description = "Get all the groups in the Flashfood application.")
  List<FamilyResponse> getAllGroups(Long userId);

  @Operation(description = "Unlink user from group in the Flashfood application.")
  void unlinkGroup(Long userId, Long groupId);

  @Operation(description = "Link an userId to a group in the Flashfood application.")
  void linkGroup(Long userId, Long groupId);
}
