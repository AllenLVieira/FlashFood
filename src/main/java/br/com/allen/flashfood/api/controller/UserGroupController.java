package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.FamilyModelAssembler;
import br.com.allen.flashfood.api.model.response.FamilyResponse;
import br.com.allen.flashfood.domain.model.User;
import br.com.allen.flashfood.domain.service.UserRegistrationsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users/{userId}/groups")
@AllArgsConstructor
public class UserGroupController {

    private UserRegistrationsService userService;
    private FamilyModelAssembler groupAssembler;

    @GetMapping
    public List<FamilyResponse> getAllGroups(@PathVariable Long userId) {
        User user = userService.findUserOrElseThrow(userId);
        return groupAssembler.toCollectionModel(user.getGroups());
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlinkGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        userService.unlinkGroup(userId, groupId);
    }
}
