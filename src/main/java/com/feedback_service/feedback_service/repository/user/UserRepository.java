package com.feedback_service.feedback_service.repository.user;

import com.feedback_service.feedback_service.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
