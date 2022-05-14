package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.PermissionNotFoundException;
import br.com.allen.flashfood.domain.model.Permission;
import br.com.allen.flashfood.domain.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionRegistrationService {

    @Autowired
    private PermissionRepository repository;

    public Permission findPermissionOrElseThrow(Long permissionId) {
        return repository.findById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException(permissionId));
    }
}
