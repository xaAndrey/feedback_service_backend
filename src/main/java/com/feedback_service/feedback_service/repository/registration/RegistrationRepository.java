package com.feedback_service.feedback_service.repository.registration;

import com.feedback_service.feedback_service.entity.registration.RegistrationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Integer> {
    List<RegistrationEntity> findAllByOrderById(Pageable page);
}
