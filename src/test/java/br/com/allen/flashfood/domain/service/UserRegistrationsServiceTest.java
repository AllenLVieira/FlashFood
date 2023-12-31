package br.com.allen.flashfood.domain.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.allen.flashfood.domain.exception.BusinessException;
import br.com.allen.flashfood.domain.exception.FamilyNotFoundException;
import br.com.allen.flashfood.domain.exception.UserNotFoundException;
import br.com.allen.flashfood.domain.model.Family;
import br.com.allen.flashfood.domain.model.User;
import br.com.allen.flashfood.domain.repository.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserRegistrationsService.class})
@ExtendWith(SpringExtension.class)
class UserRegistrationsServiceTest {
  User user;
  @Autowired private UserRegistrationsService underTest;
  @MockBean private FamilyRegistrationService familyRegistrationService;
  @MockBean private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    user = new User();
    user.setEmail("allenvieira96@gmail.com");
    user.setGroups(new HashSet<>());
    user.setId(123L);
    user.setName("Allen");
    user.setPassword("123456");
    user.setRegistrationDate(null);
  }

  /** Method under test: {@link UserRegistrationsService#saveUser(User)} */
  @Test
  void shouldSuccessfullySaveUser() {
    Optional<User> ofResult = Optional.of(user);
    underTest.saveUser(user);

    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userArgumentCaptor.capture());
    User capturedUser = userArgumentCaptor.getValue();
    assertThat(capturedUser).isEqualTo(user);
  }

  /** Method under test: {@link UserRegistrationsService#saveUser(User)} */
  @Test
  void shouldThrowBusinessExceptionWhenDifferentUsersSameEmail() {
    User user2 = new User();
    user2.setEmail("allenvieira96@gmail.com");
    user2.setGroups(new HashSet<>());
    user2.setId(2L);
    user2.setName("Allen2");
    user2.setPassword("123456");
    user2.setRegistrationDate(null);
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findByEmail(any())).thenReturn(ofResult);
    doNothing().when(userRepository).detach(any());

    assertThrows(BusinessException.class, () -> underTest.saveUser(user2));
    verify(userRepository).findByEmail(any());
    verify(userRepository).detach(any());
  }

  /** Method under test: {@link UserRegistrationsService#saveUser(User)} */
  @Test
  void shouldSaveWhenDifferentUsersDifferentEmail() {
    User user2 = new User();
    user2.setEmail("allenvieira962@gmail.com");
    user2.setGroups(new HashSet<>());
    user2.setId(2L);
    user2.setName("Allen2");
    user2.setPassword("123456");
    user2.setRegistrationDate(null);
    Optional<User> ofResult = Optional.of(user2);
    when(userRepository.findByEmail(any())).thenReturn(ofResult);
    doNothing().when(userRepository).detach(any());
    underTest.saveUser(user2);

    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userArgumentCaptor.capture());
    User capturedUser = userArgumentCaptor.getValue();
    assertThat(capturedUser).isEqualTo(user2);
  }

  /** Method under test: {@link UserRegistrationsService#changePassword(Long, String, String)} */
  @Test
  void shouldChangePasswordWithSuccess() {
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findById(any())).thenReturn(ofResult);
    underTest.changePassword(123L, "123456", "654321");
    verify(userRepository).findById(any());
  }

  /** Method under test: {@link UserRegistrationsService#changePassword(Long, String, String)} */
  @Test
  void shouldThrowBusinessExceptionWhenUserEnterWrongPassword() {
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findById(any())).thenReturn(ofResult);
    assertThrows(
        BusinessException.class, () -> underTest.changePassword(123L, "wrongpassword", "654321"));
    verify(userRepository).findById(any());
  }

  /** Method under test: {@link UserRegistrationsService#changePassword(Long, String, String)} */
  @Test
  void shouldThrowUserNotFoundExceptionWhenUserDoesNotExists() {
    when(userRepository.findById(any())).thenReturn(Optional.empty());
    assertThrows(
        UserNotFoundException.class, () -> underTest.changePassword(123L, "123456", "654321"));
  }

  /** Method under test: {@link UserRegistrationsService#unlinkGroup(Long, Long)} */
  @Test
  void testUnlinkGroup() {
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findById(any())).thenReturn(ofResult);

    Family family = new Family();
    family.setId(123L);
    family.setName("Group");
    family.setPermissions(new HashSet<>());

    user.getGroups().add(family);

    when(familyRegistrationService.findFamilyOrElseThrow(any())).thenReturn(family);
    underTest.unlinkGroup(123L, 123L);
    verify(userRepository).findById(any());
    verify(familyRegistrationService).findFamilyOrElseThrow(any());
    assertFalse(user.getGroups().contains(family));
  }

  /** Method under test: {@link UserRegistrationsService#unlinkGroup(Long, Long)} */
  @Test
  void unlinkGroupShouldThrowFamilyNotFoundExceptionWhenFamilyDoesNotExists() {
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findById(any())).thenReturn(ofResult);
    when(familyRegistrationService.findFamilyOrElseThrow(any()))
        .thenThrow(new FamilyNotFoundException("An error occurred"));
    assertThrows(FamilyNotFoundException.class, () -> underTest.unlinkGroup(123L, 123L));
    verify(userRepository).findById(any());
    verify(familyRegistrationService).findFamilyOrElseThrow(any());
  }

  /** Method under test: {@link UserRegistrationsService#unlinkGroup(Long, Long)} */
  @Test
  void unlinkGroupShouldThrowUserNotFoundExceptionWhenUserDoesNotExists() {
    Family family = new Family();
    family.setId(123L);
    family.setName("Group");
    family.setPermissions(new HashSet<>());

    user.getGroups().add(family);
    when(userRepository.findById(any())).thenThrow(new UserNotFoundException("An error occurred"));
    when(familyRegistrationService.findFamilyOrElseThrow(any())).thenReturn(family);
    assertThrows(UserNotFoundException.class, () -> underTest.unlinkGroup(123L, 123L));
    verify(userRepository).findById(any());
  }

  /** Method under test: {@link UserRegistrationsService#linkGroup(Long, Long)} */
  @Test
  void shouldSuccessfullyLinkGroupToUser() {
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findById(any())).thenReturn(ofResult);

    Family family = new Family();
    family.setId(123L);
    family.setName("Allen");
    family.setPermissions(new HashSet<>());
    when(familyRegistrationService.findFamilyOrElseThrow(any())).thenReturn(family);
    underTest.linkGroup(123L, 123L);
    verify(userRepository).findById(any());
    verify(familyRegistrationService).findFamilyOrElseThrow(any());
    assertTrue(user.getGroups().contains(family));
  }

  /** Method under test: {@link UserRegistrationsService#linkGroup(Long, Long)} */
  @Test
  void linkGroupshouldThrowFamilyNotFoundExceptionWhenFamilyDoesNotExists() {
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findById(any())).thenReturn(ofResult);
    when(familyRegistrationService.findFamilyOrElseThrow(any()))
        .thenThrow(new FamilyNotFoundException("An error occurred"));
    assertThrows(FamilyNotFoundException.class, () -> underTest.linkGroup(123L, 123L));
    verify(userRepository).findById(any());
    verify(familyRegistrationService).findFamilyOrElseThrow(any());
  }

  /** Method under test: {@link UserRegistrationsService#linkGroup(Long, Long)} */
  @Test
  void linkGroupShouldThrowUserNotFoundExceptionWhenUserDoesNotExists() {
    Family family = new Family();
    family.setId(123L);
    family.setName("Group");
    family.setPermissions(new HashSet<>());

    user.getGroups().add(family);
    when(userRepository.findById(any())).thenThrow(new UserNotFoundException("An error occurred"));
    when(familyRegistrationService.findFamilyOrElseThrow(any())).thenReturn(family);
    assertThrows(UserNotFoundException.class, () -> underTest.linkGroup(123L, 123L));
    verify(userRepository).findById(any());
  }

  /** Method under test: {@link UserRegistrationsService#findUserOrElseThrow(Long)} */
  @Test
  void shouldSuccessfullyFindUserById() {
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findById(any())).thenReturn(ofResult);
    assertSame(user, underTest.findUserOrElseThrow(123L));
    verify(userRepository).findById(any());
  }

  /** Method under test: {@link UserRegistrationsService#findUserOrElseThrow(Long)} */
  @Test
  void findByUserIdShouldThrowUserNotFoundException() {
    when(userRepository.findById(any())).thenReturn(Optional.empty());
    assertThrows(UserNotFoundException.class, () -> underTest.findUserOrElseThrow(123L));
    verify(userRepository).findById(any());
  }
}
