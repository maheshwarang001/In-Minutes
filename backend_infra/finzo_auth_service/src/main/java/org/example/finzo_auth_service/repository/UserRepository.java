package org.example.finzo_auth_service.repository;

import org.example.finzo_auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);


    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

}
