package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.PermissionModelAssembler;
import br.com.allen.flashfood.api.model.response.PermissionResponse;
import br.com.allen.flashfood.domain.model.Family;
import br.com.allen.flashfood.domain.service.FamilyRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
    value = "/groups/{familyId}/permissions",
    produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(
    name = "Family Permission",
    description =
        "Responsible for managing permissions associated with family entities"
            + " in the FlashFood application. This controller handles operations like granting, updating, and revoking"
            + " permissions for different family roles or members. It plays a crucial role in maintaining the security and"
            + " access control within the family context, ensuring appropriate access rights are given to various"
            + " family-related features.")
public class FamilyPermissionController {
  private final FamilyRegistrationService familyService;
  private final PermissionModelAssembler permissionAssembler;

  @GetMapping
  @Operation(description = "Get all the permission by Family Id in the Flashfood application.")
  public List<PermissionResponse> getAllPermissionsByFamilyId(@PathVariable Long familyId) {
    Family family = familyService.findFamilyOrElseThrow(familyId);
    return permissionAssembler.toCollectionModel(family.getPermissions());
  }

  @DeleteMapping("/{permissionId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Unlink a permission from a Family in the Flashfood application.")
  public void unlinkPermission(@PathVariable Long familyId, @PathVariable Long permissionId) {
    familyService.unlinkPermission(familyId, permissionId);
  }

  @PutMapping("/{permissionId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Update permissions in a Family Id in the Flashfood application.")
  public void linkPermission(@PathVariable Long familyId, @PathVariable Long permissionId) {
    familyService.linkPermission(familyId, permissionId);
  }
}
