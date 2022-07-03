package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.FamilyNotFoundException;
import br.com.allen.flashfood.domain.model.Family;
import br.com.allen.flashfood.domain.model.Permission;
import br.com.allen.flashfood.domain.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FamilyRegistrationService {
    public static final String FAMILY_IN_USE = "Family with %d code cannot be removed because it is in use.";
    
    private final FamilyRepository familyRepository;
    private final PermissionRegistrationService permissionService;

    @Transactional
    public Family saveFamily(Family family) {
        return familyRepository.save(family);
    }

    @Transactional
    public void deleteFamily(Long familyId) {
        try {
            familyRepository.deleteById(familyId);
            familyRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new FamilyNotFoundException(familyId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(FAMILY_IN_USE, familyId));
        }
    }

    @Transactional
    public void linkPermission(Long familyId, Long permissionId) {
        Family family = findFamilyOrElseThrow(familyId);
        Permission permission = permissionService.findPermissionOrElseThrow(permissionId);
        family.addPermissions(permission);
    }

    @Transactional
    public void unlinkPermission(Long familyId, Long permissionId) {
        Family family = findFamilyOrElseThrow(familyId);
        Permission permission = permissionService.findPermissionOrElseThrow(permissionId);
        family.removePermissions(permission);
    }

    public Family findFamilyOrElseThrow(Long familyId) {
        return familyRepository.findById(familyId)
                .orElseThrow(() -> new FamilyNotFoundException(familyId));
    }
}
