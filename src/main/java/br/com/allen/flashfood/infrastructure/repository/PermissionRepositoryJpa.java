package br.com.allen.flashfood.infrastructure.repository;

import br.com.allen.flashfood.domain.model.Permission;
import br.com.allen.flashfood.domain.repository.PermissionRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class PermissionRepositoryJpa implements PermissionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Permission> getAllPermissions() {
        return entityManager.createQuery("from Permission", Permission.class)
                .getResultList();
    }

    @Override
    public Permission getPermissionById(Long id) {
        return entityManager.find(Permission.class, id);
    }

    @Transactional
    @Override
    public Permission addPermission(Permission permission) {
        return entityManager.merge(permission);
    }

    @Transactional
    @Override
    public void removePermission(Permission permission) {
        permission = getPermissionById(permission.getId());
        entityManager.remove(permission);
    }
}
