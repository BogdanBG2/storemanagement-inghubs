package com.bogdan.storemanagement_inghubs.controller;

import com.bogdan.storemanagement_inghubs.dto.UserDTO;
import com.bogdan.storemanagement_inghubs.model.User;
import com.bogdan.storemanagement_inghubs.model.constants.UserRole;
import com.bogdan.storemanagement_inghubs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Page<User>> getAllUsers(Pageable pageable,
                                                @RequestParam(required = false) String nameSubstring,
                                                @RequestParam(required = false) UserRole role) {
    return ResponseEntity.ok(userService.findAll(pageable, nameSubstring, role));
  }

  @GetMapping("/me")
  public ResponseEntity<UserDTO> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
    return userService.findByUsername(userDetails.getUsername())
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/me")
  public ResponseEntity<Void> deleteMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
    try {
      userService.deleteByUsername(userDetails.getUsername());
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<User> getUserById(@PathVariable UUID id) {
    return userService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/{id}/role")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Void> changeUserRole(@AuthenticationPrincipal UserDetails userDetails,
                                             @PathVariable UUID id,
                                             @RequestParam UserRole role) {
    try {
      userService.changeUserRole(userDetails.getUsername(), id, role);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
