package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.Permission;

import java.util.List;

public interface PermissionRepository {
    List<Permission> getAllPermissions();

    Permission getPermissionById(Long id);

    Permission savePermission(Permission permission);

    void removePermission(Permission permission);

}
