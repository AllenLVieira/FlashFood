package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.PermissionNotFoundException;
import br.com.allen.flashfood.domain.model.Permission;
import br.com.allen.flashfood.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionRegistrationService {

  private final PermissionRepository repository;

  public Permission findPermissionOrElseThrow(Long permissionId) {
    return repository
        .findById(permissionId)
        .orElseThrow(() -> new PermissionNotFoundException(permissionId));
  }
}
