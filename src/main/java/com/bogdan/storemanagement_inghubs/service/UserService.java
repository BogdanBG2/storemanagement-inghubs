package com.bogdan.storemanagement_inghubs.service;

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
}
