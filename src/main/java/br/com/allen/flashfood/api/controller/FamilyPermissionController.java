package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.PermissionModelAssembler;
import br.com.allen.flashfood.api.model.response.PermissionResponse;
import br.com.allen.flashfood.domain.model.Family;
import br.com.allen.flashfood.domain.service.FamilyRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/groups/{familyId}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class FamilyPermissionController {

    @Autowired
    private FamilyRegistrationService familyService;

    @Autowired
    private PermissionModelAssembler permissionAssembler;

    @GetMapping
    public List<PermissionResponse> getAllPermissionsByFamilyId(@PathVariable Long familyId) {
        Family family = familyService.findFamilyOrElseThrow(familyId);
        return permissionAssembler.toCollectionModel(family.getPermissions());
    }

    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlinkPermission(@PathVariable Long familyId,
                                 @PathVariable Long permissionId) {
        familyService.unlinkPermission(familyId, permissionId);
    }

    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void linkPermission(@PathVariable Long familyId,
                               @PathVariable Long permissionId) {
        familyService.linkPermission(familyId, permissionId);
    }
}
