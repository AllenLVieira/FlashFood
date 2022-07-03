package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.PermissionNotFoundException;
import br.com.allen.flashfood.domain.model.Permission;
import br.com.allen.flashfood.domain.repository.PermissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PermissionRegistrationService.class})
@ExtendWith(SpringExtension.class)
class PermissionRegistrationServiceTest {
    @Autowired
    private PermissionRegistrationService underTest;

    @MockBean
    private PermissionRepository permissionRepository;

    /**
     * Method under test: {@link PermissionRegistrationService#findPermissionOrElseThrow(Long)}
     */
    @Test
    void shouldFindPermissionById() {
        Permission permission = new Permission();
        permission.setDescription("Description");
        permission.setId(123L);
        permission.setName("Name");
        Optional<Permission> ofResult = Optional.of(permission);
        when(permissionRepository.findById(any())).thenReturn(ofResult);
        assertSame(permission, underTest.findPermissionOrElseThrow(123L));
        verify(permissionRepository).findById(any());
    }

    /**
     * Method under test: {@link PermissionRegistrationService#findPermissionOrElseThrow(Long)}
     */
    @Test
    void shouldThrowPermissionNotFoundExceptionWhenEmptyPermission() {
        when(permissionRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(PermissionNotFoundException.class,
                () -> underTest.findPermissionOrElseThrow(123L));
        verify(permissionRepository).findById(any());
    }

    /**
     * Method under test: {@link PermissionRegistrationService#findPermissionOrElseThrow(Long)}
     */
    @Test
    void shouldThrowPermissionNotFoundExceptionWhenPermissionIdDoesNotExists() {
        when(permissionRepository.findById(any())).thenThrow(new PermissionNotFoundException("An error occurred"));
        assertThrows(PermissionNotFoundException.class,
                () -> underTest.findPermissionOrElseThrow(123L));
        verify(permissionRepository).findById(any());
    }
}

