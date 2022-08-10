package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.FamilyModelAssembler;
import br.com.allen.flashfood.api.model.response.FamilyResponse;
import br.com.allen.flashfood.domain.model.User;
import br.com.allen.flashfood.domain.service.UserRegistrationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users/{userId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserGroupController {

    private final UserRegistrationsService userService;
    private final FamilyModelAssembler groupAssembler;

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

    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void linkGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        userService.linkGroup(userId, groupId);
    }
}
