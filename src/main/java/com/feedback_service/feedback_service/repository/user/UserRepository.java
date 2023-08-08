package com.feedback_service.feedback_service.repository.user;

import com.feedback_service.feedback_service.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> getBySecurityStamp(String securityStamp);
    Optional<UserEntity> findByUsername(String username);
    boolean existsByIdIsGreaterThan(Integer id);
}
