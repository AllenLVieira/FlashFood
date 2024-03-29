package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.FamilyModelAssembler;
import br.com.allen.flashfood.api.assembler.FamilyRequestDisassembler;
import br.com.allen.flashfood.api.controller.openapi.FamilyControllerOpenApi;
import br.com.allen.flashfood.api.model.request.FamilyRequest;
import br.com.allen.flashfood.api.model.response.FamilyResponse;
import br.com.allen.flashfood.domain.model.Family;
import br.com.allen.flashfood.domain.repository.FamilyRepository;
import br.com.allen.flashfood.domain.service.FamilyRegistrationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FamilyController implements FamilyControllerOpenApi {
  private final FamilyRepository familyRepository;
  private final FamilyRegistrationService familyRegistrationService;
  private final FamilyModelAssembler familyModelAssembler;
  private final FamilyRequestDisassembler familyRequestDisassembler;

  @GetMapping(value = "/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public FamilyResponse findFamilyById(@PathVariable Long groupId) {
    Family group = familyRegistrationService.findFamilyOrElseThrow(groupId);
    return familyModelAssembler.toModel(group);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<FamilyResponse> getAllGroups() {
    List<Family> allGroups = familyRepository.findAll();
    return familyModelAssembler.toCollectionModel(allGroups);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public FamilyResponse addFamily(@RequestBody @Valid FamilyRequest familyRequest) {
    Family group = familyRequestDisassembler.toDomainObject(familyRequest);
    group = familyRegistrationService.saveFamily(group);
    return familyModelAssembler.toModel(group);
  }

  @PutMapping(value = "/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public FamilyResponse updateFamily(
      @PathVariable Long groupId, @RequestBody @Valid FamilyRequest familyRequest) {
    Family actualGroup = familyRegistrationService.findFamilyOrElseThrow(groupId);
    familyRequestDisassembler.copyToDomainObject(familyRequest, actualGroup);
    actualGroup = familyRegistrationService.saveFamily(actualGroup);
    return familyModelAssembler.toModel(actualGroup);
  }

  @DeleteMapping("/{groupId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteFamily(@PathVariable Long groupId) {
    familyRegistrationService.deleteFamily(groupId);
  }
}
