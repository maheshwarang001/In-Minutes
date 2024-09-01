package org.example.cartuserservice.repository;

import org.example.cartuserservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {


    @Query("SELECT us FROM User us WHERE us.userId = :userId")
    User findUserByUserId(@Param("userId")UUID userId);


}
