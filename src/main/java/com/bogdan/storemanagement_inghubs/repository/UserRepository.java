package com.bogdan.storemanagement_inghubs.repository;

import com.bogdan.storemanagement_inghubs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
  Optional<User> findByUsername(String username);
  boolean existsByUsername(String username);
  void deleteByUsername(String username);
}
