package com.healthclaimx.claim.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.healthclaimx.claim.entity.Claim;
public interface ClaimRepository extends JpaRepository<Claim, Long> {
}