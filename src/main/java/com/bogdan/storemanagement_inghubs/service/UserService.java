package com.bogdan.storemanagement_inghubs.service;

import com.bogdan.storemanagement_inghubs.dto.UserRegisterDTO;
import com.bogdan.storemanagement_inghubs.model.User;
import com.bogdan.storemanagement_inghubs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public User createUser(UserRegisterDTO userRegisterDTO) {
    User newUser = User.fromRegisterDTO(userRegisterDTO);
    return userRepository.save(newUser);
  }
}
