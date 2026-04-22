package com.healthclaimx.auth.repository;

import com.healthclaimx.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.zone = :zone")
    Optional<User> findByUsernameAndZone(@Param("username") String username, @Param("zone") String zone);
    
    @Query("SELECT u FROM User u WHERE u.zone = :zone")
    List<User> findAllByZone(@Param("zone") String zone);
    
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.zone = :zone")
    Optional<User> findByIdAndZone(@Param("id") Long id, @Param("zone") String zone);
}
