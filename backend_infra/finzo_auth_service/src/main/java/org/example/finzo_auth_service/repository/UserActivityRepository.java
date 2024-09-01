package org.example.finzo_auth_service.repository;

import org.example.finzo_auth_service.entity.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;
@NoRepositoryBean
public interface UserActivityRepository extends JpaRepository<UserActivity, UUID> {
}
