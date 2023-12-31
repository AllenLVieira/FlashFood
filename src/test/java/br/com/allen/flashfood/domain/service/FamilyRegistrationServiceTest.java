package br.com.allen.flashfood.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.FamilyNotFoundException;
import br.com.allen.flashfood.domain.exception.PermissionNotFoundException;
import br.com.allen.flashfood.domain.model.Family;
import br.com.allen.flashfood.domain.model.Permission;
import br.com.allen.flashfood.domain.repository.FamilyRepository;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FamilyRegistrationService.class})
@ExtendWith(SpringExtension.class)
class FamilyRegistrationServiceTest {
  Family family;
  @Autowired private FamilyRegistrationService underTest;
  @MockBean private FamilyRepository familyRepository;
  @MockBean private PermissionRegistrationService permissionRegistrationService;

  @BeforeEach
  void setUp() {
    family = new Family();
    family.setId(123L);
    family.setName("Name");
    family.setPermissions(new HashSet<>());
  }

  /** Method under test: {@link FamilyRegistrationService#saveFamily(Family)} */
  @Test
  void testSaveFamily() {
    when(familyRepository.save(any())).thenReturn(family);
    assertSame(family, underTest.saveFamily(family));
    verify(familyRepository).save(any());
  }

  /** Method under test: {@link FamilyRegistrationService#saveFamily(Family)} */
  @Test
  void shouldThrowEmptyResultDataAccessExceptionWhenSaveFamily() {
    when(familyRepository.save(any())).thenThrow(new EmptyResultDataAccessException(3));
    assertThrows(EmptyResultDataAccessException.class, () -> underTest.saveFamily(family));
    verify(familyRepository).save(any());
  }

  /** Method under test: {@link FamilyRegistrationService#deleteFamily(Long)} */
  @Test
  void shouldDeleteFamily() {
    doNothing().when(familyRepository).flush();
    doNothing().when(familyRepository).deleteById(any());
    underTest.deleteFamily(123L);
    verify(familyRepository).flush();
    verify(familyRepository).deleteById(any());
  }

  /** Method under test: {@link FamilyRegistrationService#deleteFamily(Long)} */
  @Test
  void shouldThrowFamilyNotFoundExceptionWhenDeleteFamilyById() {
    doNothing().when(familyRepository).flush();
    doThrow(new EmptyResultDataAccessException(3)).when(familyRepository).deleteById(any());
    assertThrows(FamilyNotFoundException.class, () -> underTest.deleteFamily(123L));
    verify(familyRepository).deleteById(any());
  }

  /** Method under test: {@link FamilyRegistrationService#deleteFamily(Long)} */
  @Test
  void shouldThrowEntityInUseExceptionWhenDeleteFamilyById() {
    doNothing().when(familyRepository).flush();
    doThrow(new DataIntegrityViolationException("An error occurred"))
        .when(familyRepository)
        .deleteById(any());
    assertThrows(EntityInUseException.class, () -> underTest.deleteFamily(123L));
    verify(familyRepository).deleteById(any());
  }

  /** Method under test: {@link FamilyRegistrationService#linkPermission(Long, Long)} */
  @Test
  void shouldLinkPermissionToFamily() {
    Optional<Family> ofResult = Optional.of(family);
    when(familyRepository.findById(any())).thenReturn(ofResult);

    Permission permission = new Permission();
    permission.setDescription("Description");
    permission.setId(123L);
    permission.setName("Name");
    when(permissionRegistrationService.findPermissionOrElseThrow(any())).thenReturn(permission);
    underTest.linkPermission(123L, 123L);
    assertTrue(family.getPermissions().contains(permission));
    verify(familyRepository).findById(any());
    verify(permissionRegistrationService).findPermissionOrElseThrow(any());
  }

  /** Method under test: {@link FamilyRegistrationService#linkPermission(Long, Long)} */
  @Test
  void shouldThrowPermissionNotFoundExceptionWhenPermissionDoesNotExists() {
    Optional<Family> ofResult = Optional.of(family);
    when(familyRepository.findById(any())).thenReturn(ofResult);
    when(permissionRegistrationService.findPermissionOrElseThrow(any()))
        .thenThrow(new PermissionNotFoundException("An error ocurred"));
    assertThrows(PermissionNotFoundException.class, () -> underTest.linkPermission(123L, 123L));
    verify(familyRepository).findById(any());
    verify(permissionRegistrationService).findPermissionOrElseThrow(any());
  }

  /** Method under test: {@link FamilyRegistrationService#linkPermission(Long, Long)} */
  @Test
  void shouldThrowFamilyNotFoundExceptionWhenFamilyDoesNotExists() {
    when(familyRepository.findById(any())).thenReturn(Optional.empty());

    Permission permission = new Permission();
    permission.setDescription("Description");
    permission.setId(123L);
    permission.setName("Name");
    when(permissionRegistrationService.findPermissionOrElseThrow(any())).thenReturn(permission);
    assertThrows(FamilyNotFoundException.class, () -> underTest.linkPermission(123L, 123L));
    verify(familyRepository).findById(any());
  }

  /** Method under test: {@link FamilyRegistrationService#unlinkPermission(Long, Long)} */
  @Test
  void shouldUnlinkPermissionFromFamily() {
    Permission permission = new Permission();
    permission.setDescription("Description");
    permission.setId(123L);
    permission.setName("Name");
    family.getPermissions().add(permission);

    Optional<Family> ofResult = Optional.of(family);
    when(familyRepository.findById(any())).thenReturn(ofResult);

    when(permissionRegistrationService.findPermissionOrElseThrow(any())).thenReturn(permission);
    underTest.unlinkPermission(123L, 123L);
    assertFalse(family.getPermissions().contains(permission));
    verify(familyRepository).findById(any());
    verify(permissionRegistrationService).findPermissionOrElseThrow(any());
  }

  /** Method under test: {@link FamilyRegistrationService#unlinkPermission(Long, Long)} */
  @Test
  void unlinkShouldThrowPermissionNotFoundExceptionWhenFamilyDoesNotExists() {
    Optional<Family> ofResult = Optional.of(family);
    when(familyRepository.findById(any())).thenReturn(ofResult);
    when(permissionRegistrationService.findPermissionOrElseThrow(any()))
        .thenThrow(new PermissionNotFoundException("An error ocurred"));
    assertThrows(PermissionNotFoundException.class, () -> underTest.unlinkPermission(123L, 123L));
    verify(familyRepository).findById(any());
    verify(permissionRegistrationService).findPermissionOrElseThrow(any());
  }

  /** Method under test: {@link FamilyRegistrationService#unlinkPermission(Long, Long)} */
  @Test
  void unlinkShouldThrowFamilyNotFoundExceptionWhenFamilyDoesNotExists() {
    Optional<Family> ofResult = Optional.of(family);
    when(familyRepository.findById(any())).thenReturn(ofResult);
    when(permissionRegistrationService.findPermissionOrElseThrow(any()))
        .thenThrow(new FamilyNotFoundException("An error ocurred"));
    assertThrows(FamilyNotFoundException.class, () -> underTest.unlinkPermission(123L, 123L));
    verify(familyRepository).findById(any());
    verify(permissionRegistrationService).findPermissionOrElseThrow(any());
  }

  /** Method under test: {@link FamilyRegistrationService#findFamilyOrElseThrow(Long)} */
  @Test
  void shouldFindFamilyById() {
    Optional<Family> ofResult = Optional.of(family);
    when(familyRepository.findById(any())).thenReturn(ofResult);
    assertSame(family, underTest.findFamilyOrElseThrow(123L));
    verify(familyRepository).findById(any());
  }

  /** Method under test: {@link FamilyRegistrationService#findFamilyOrElseThrow(Long)} */
  @Test
  void shouldThrowFamilyNotFoundExceptionWhenTryToFindEmptyFamily() {
    when(familyRepository.findById(any())).thenReturn(Optional.empty());
    assertThrows(FamilyNotFoundException.class, () -> underTest.findFamilyOrElseThrow(123L));
    verify(familyRepository).findById(any());
  }
}
