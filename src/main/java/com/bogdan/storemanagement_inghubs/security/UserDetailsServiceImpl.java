package com.bogdan.storemanagement_inghubs.security;

import com.bogdan.storemanagement_inghubs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .map(user -> new UserDetails() {
          @Override
          public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of();
          }

          @Override
          public String getPassword() {
            return "";
          }

          @Override
          public String getUsername() {
            return "";
          }
        })
  }
}
