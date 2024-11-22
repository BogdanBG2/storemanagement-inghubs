package com.bogdan.storemanagement_inghubs.service;

import com.bogdan.storemanagement_inghubs.dto.UserDTO;
import com.bogdan.storemanagement_inghubs.dto.UserRegisterDTO;
import com.bogdan.storemanagement_inghubs.model.User;
import com.bogdan.storemanagement_inghubs.model.constants.UserRole;
import com.bogdan.storemanagement_inghubs.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  private User getMockUser() {
    User user = new User();
    user.setId(new UUID(1, 1));
    user.setName("John Doe");
    user.setUsername("johndoe");
    user.setPassword("password");
    user.setRole(UserRole.ADMIN);
    return user;
  }

  @Test
  void testFindById() {
    User user = getMockUser();
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    Optional<User> foundUser = userService.findById(user.getId());
    assertEquals(Optional.of(user), foundUser);
  }

  @Test
  void testFindByUsername() {
    when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(getMockUser()));
    Optional<UserDTO> foundUser = userService.findByUsername("johndoe");
    assertTrue(foundUser.isPresent());
    assertEquals(getMockUser().getId(), foundUser.get().id());
  }

  @Test
  void testFindEntityByUsername() {
    when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(getMockUser()));
    Optional<User> foundUser = userService.findEntityByUsername("johndoe");
    assertTrue(foundUser.isPresent());
    assertEquals(getMockUser(), foundUser.get());
  }

  @Test
  void testExistsByUsername() {
    when(userRepository.existsByUsername("johndoe")).thenReturn(true);
    boolean exists = userService.existsByUsername("johndoe");
    assertTrue(exists);
  }

  @Test
  void testCreateUser() {
    User user = getMockUser();
    when(userRepository.save(any())).thenReturn(user);
    userService.createUser(new UserRegisterDTO(user.getName(), user.getUsername(), user.getPassword()));
    verify(userRepository).save(any());
  }

  @Test
  void testDeleteByUsername() {
    doNothing().when(userRepository).deleteByUsername("johndoe");
    userService.deleteByUsername("johndoe");
    verify(userRepository).deleteByUsername("johndoe");
  }

  @Test
  void testChangeUserRole() {
    User user = getMockUser();
    when(userRepository.findById(any())).thenReturn(Optional.of(user));
    when(userRepository.save(any())).thenReturn(user);
    userService.changeUserRole("johndoe-2", user.getId(), UserRole.ADMIN);
    verify(userRepository).save(any());
  }

  @Test
  void testChangeUserRole_sameUser() {
    User user = getMockUser();
    when(userRepository.findById(any())).thenReturn(Optional.of(user));
    assertThrows(IllegalArgumentException.class, () -> userService.changeUserRole("johndoe", user.getId(), UserRole.ADMIN));
    verify(userRepository, never()).save(any());
  }

  @Test
  void testFindAll() {
    Pageable pageable = mock(Pageable.class);
    String nameSubString = "Test";
    UserRole role = UserRole.ADMIN;
    Page<User> page = Page.empty();
    page = page.map(p -> getMockUser());
    when(userRepository.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(page);
    Page<User> result = userService.findAll(pageable, nameSubString, role);
    assertEquals(page, result);
  }
}