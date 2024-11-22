package com.bogdan.storemanagement_inghubs.service;

import com.bogdan.storemanagement_inghubs.dto.UserDTO;
import com.bogdan.storemanagement_inghubs.dto.UserRegisterDTO;
import com.bogdan.storemanagement_inghubs.model.User;
import com.bogdan.storemanagement_inghubs.model.constants.UserRole;
import com.bogdan.storemanagement_inghubs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public Optional<UserDTO> findByUsername(String username) {
    return userRepository.findByUsername(username)
        .map(UserDTO::fromUser);
  }

  public Optional<User> findEntityByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public User createUser(UserRegisterDTO userRegisterDTO) {
    User newUser = User.fromRegisterDTO(userRegisterDTO);
    newUser.setPassword(new BCryptPasswordEncoder().encode(userRegisterDTO.password()));
    return userRepository.save(newUser);
  }

  public Page<UserDTO> findAll(Pageable pageable,
                            String nameSubstring,
                            UserRole role) {
    return userRepository.findAll(((root, query, criteriaBuilder) -> {
      if (query == null) {
        return null;
      }
      if (nameSubstring != null) {
        query.where(criteriaBuilder.like(root.get("name"), "%" + nameSubstring + "%"));
      }
      if (role != null) {
        query.where(criteriaBuilder.equal(root.get("role"), role));
      }
      return query.getRestriction();
    }), pageable).map(UserDTO::fromUser);
  }

  public void deleteByUsername(String username) {
    userRepository.deleteByUsername(username);
  }

  public Optional<UserDTO> findById(UUID id) {
    return userRepository.findById(id)
        .map(UserDTO::fromUser);
  }

  private Optional<User> findEntityById(UUID id) {
    return userRepository.findById(id);
  }

  public void changeUserRole(String username, UUID   id, UserRole role) {
    User user = findEntityById(id).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    if (user.getUsername().equals(username)) {
      throw new IllegalArgumentException("You cannot change your own role.");
    }
    user.setRole(role);
    userRepository.save(user);
  }
}
