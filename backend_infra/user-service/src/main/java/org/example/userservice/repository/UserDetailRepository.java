package org.example.userservice.repository;

import jakarta.persistence.Entity;
import org.example.userservice.entity.user.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, UUID> {

    @Query("SELECT u FROM UserDetail u WHERE u.user_id = :id")
    UserDetail findByUserId(UUID id);
}
