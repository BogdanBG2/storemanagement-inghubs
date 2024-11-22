package com.bogdan.storemanagement_inghubs.controller;

import com.bogdan.storemanagement_inghubs.dto.UserLoginDTO;
import com.bogdan.storemanagement_inghubs.dto.UserRegisterDTO;
import com.bogdan.storemanagement_inghubs.service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody UserRegisterDTO userRegisterDTO) {
    try {
      authService.register(userRegisterDTO);
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDTO userLoginDTO) {
    try {
      return ResponseEntity.ok(authService.login(userLoginDTO));
    } catch (BadCredentialsException e) {
      return ResponseEntity.badRequest().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
