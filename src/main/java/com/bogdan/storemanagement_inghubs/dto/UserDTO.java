package com.bogdan.storemanagement_inghubs.dto;

import com.bogdan.storemanagement_inghubs.model.User;
import com.bogdan.storemanagement_inghubs.model.constants.UserRole;

import java.util.UUID;

public record UserDTO(
  UUID id,
  String name,
  String username,
  UserRole role
) {
  public static UserDTO fromUser(User user) {
    return new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getRole());
  }
}
