package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.model.response.PermissionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "Family Permission",
    description =
        "Responsible for managing permissions associated with family entities"
            + " in the FlashFood application. This controller handles operations like granting, updating, and revoking"
            + " permissions for different family roles or members. It plays a crucial role in maintaining the security and"
            + " access control within the family context, ensuring appropriate access rights are given to various"
            + " family-related features.")
public interface FamilyPermissionControllerOpenApi {

  @Operation(description = "Get all the permission by Family Id in the Flashfood application.")
  List<PermissionResponse> getAllPermissionsByFamilyId(@PathVariable Long familyId);

  @Operation(description = "Unlink a permission from a Family in the Flashfood application.")
  void unlinkPermission(@PathVariable Long familyId, @PathVariable Long permissionId);

  @Operation(description = "Update permissions in a Family Id in the Flashfood application.")
  void linkPermission(@PathVariable Long familyId, @PathVariable Long permissionId);
}
