package com.bogdan.storemanagement_inghubs.model;

import com.bogdan.storemanagement_inghubs.dto.UserRegisterDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.bogdan.storemanagement_inghubs.model.constants.UserRole;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String name;
  @Column(unique = true)
  private String username;
  private String password;
  @Enumerated(EnumType.STRING)
  private UserRole role;

  public static User fromRegisterDTO(UserRegisterDTO userRegisterDTO) {
    User user = new User();
    user.setName(userRegisterDTO.name());
    user.setUsername(userRegisterDTO.username());
    user.setPassword(userRegisterDTO.password());
    user.setRole(UserRole.USER);
    return user;
  }
}
