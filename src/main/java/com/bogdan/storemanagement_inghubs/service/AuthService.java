package com.bogdan.storemanagement_inghubs.service;

import com.bogdan.storemanagement_inghubs.dto.UserLoginDTO;
import com.bogdan.storemanagement_inghubs.model.User;
import com.bogdan.storemanagement_inghubs.utils.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserService userService;
  private final AuthenticationManager authenticationManager;

  public Map<String, String> login(UserLoginDTO userLoginDTO) {
    Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        userLoginDTO.username(),
        userLoginDTO.password())
    );
    if (auth == null) {
      throw new BadCredentialsException("Bad credentials for user: ");
    }
    User user = userService.findByUsername(userLoginDTO.username())
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userLoginDTO.username()));

    return Map.of("accessToken", JwtUtils.generateToken(user));
  }
}
