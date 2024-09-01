package org.example.cartservice.repository;

import org.example.cartservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT ps FROM User ps WHERE ps.userId = :uid")
    User findUserById(@Param("uid") UUID uid);

}
