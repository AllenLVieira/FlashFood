package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.FamilyModelAssembler;
import br.com.allen.flashfood.api.assembler.FamilyRequestDisassembler;
import br.com.allen.flashfood.api.model.request.FamilyRequest;
import br.com.allen.flashfood.api.model.response.FamilyResponse;
import br.com.allen.flashfood.domain.model.Family;
import br.com.allen.flashfood.domain.repository.FamilyRepository;
import br.com.allen.flashfood.domain.service.FamilyRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(
    name = "Family",
    description =
        "Manages all operations related to family entities in the FlashFood application."
            + " This controller is responsible for handling family data, including the creation of new family entries,"
            + " retrieval and updating of family details, and deletion of family records. It's essential for maintaining"
            + " family-related information, potentially important for user profiles or food preferences.")
public class FamilyController {
  private final FamilyRepository familyRepository;
  private final FamilyRegistrationService familyRegistrationService;
  private final FamilyModelAssembler familyModelAssembler;
  private final FamilyRequestDisassembler familyRequestDisassembler;

  @GetMapping("/{groupId}")
  @Operation(description = "Get a family by Id in the Flashfood application.")
  public FamilyResponse findFamilyById(@PathVariable Long groupId) {
    Family group = familyRegistrationService.findFamilyOrElseThrow(groupId);
    return familyModelAssembler.toModel(group);
  }

  @GetMapping
  @Operation(description = "Get all the families in the Flashfood application.")
  public List<FamilyResponse> getAllGroups() {
    List<Family> allGroups = familyRepository.findAll();
    return familyModelAssembler.toCollectionModel(allGroups);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(description = "Add a new Family in the Flashfood application.")
  public FamilyResponse addFamily(@RequestBody @Valid FamilyRequest familyRequest) {
    Family group = familyRequestDisassembler.toDomainObject(familyRequest);
    group = familyRegistrationService.saveFamily(group);
    return familyModelAssembler.toModel(group);
  }

  @PutMapping("/{groupId}")
  @Operation(description = "Update a Family in the Flashfood application.")
  public FamilyResponse updateFamily(
      @PathVariable Long groupId, @RequestBody @Valid FamilyRequest familyRequest) {
    Family actualGroup = familyRegistrationService.findFamilyOrElseThrow(groupId);
    familyRequestDisassembler.copyToDomainObject(familyRequest, actualGroup);
    actualGroup = familyRegistrationService.saveFamily(actualGroup);
    return familyModelAssembler.toModel(actualGroup);
  }

  @DeleteMapping("/{groupId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Delete a family by Id in the Flashfood application.")
  public void deleteFamily(@PathVariable Long groupId) {
    familyRegistrationService.deleteFamily(groupId);
  }
}
