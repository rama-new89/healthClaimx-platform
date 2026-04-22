package com.healthclaimx.claim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.healthclaimx.claim.entity.Claim;
import java.util.List;
import java.util.Optional;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    
    @Query("SELECT c FROM Claim c WHERE c.zone = :zone")
    List<Claim> findAllByZone(@Param("zone") String zone);
    
    @Query("SELECT c FROM Claim c WHERE c.id = :id AND c.zone = :zone")
    Optional<Claim> findByIdAndZone(@Param("id") Long id, @Param("zone") String zone);
    
    @Query("SELECT c FROM Claim c WHERE c.userID = :userId AND c.zone = :zone")
    List<Claim> findByUserIdAndZone(@Param("userId") Long userId, @Param("zone") String zone);
    
    @Query("SELECT c FROM Claim c WHERE c.status = :status AND c.zone = :zone")
    List<Claim> findByStatusAndZone(@Param("status") String status, @Param("zone") String zone);
}