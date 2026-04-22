package com.healthclaimx.claim.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.healthclaimx.claim.entity.Claim;
import com.healthclaimx.claim.repository.ClaimRepository;
import com.healthclaimx.claim.publisher.ClaimEventPublisher;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClaimService {
    
    private final ClaimRepository claimRepository;
    private final ClaimEventPublisher eventPublisher;
    
    public Claim createClaim(Claim claim) {
        if (claim.getZone() == null || claim.getZone().isEmpty()) {
            throw new RuntimeException("Zone must be specified for claim creation");
        }
        
        claim.setStatus("SUBMITTED");
        Claim savedClaim = claimRepository.save(claim);
        eventPublisher.publishClaimCreatedEvent(savedClaim.getId());
        return savedClaim;    
    }
    
    public List<Claim> getAllClaimsByZone(String zone) {
        return claimRepository.findAllByZone(zone);
    }
    
    public List<Claim> getALLClaims() {
        return claimRepository.findAll();
    }
    
    public Optional<Claim> getClaimByIdAndZone(Long claimId, String zone) {
        return claimRepository.findByIdAndZone(claimId, zone);
    }
    
    public List<Claim> getClaimsByUserIdAndZone(Long userId, String zone) {
        return claimRepository.findByUserIdAndZone(userId, zone);
    }
    
    public List<Claim> getClaimsByStatusAndZone(String status, String zone) {
        return claimRepository.findByStatusAndZone(status, zone);
    }
    
    public Claim updateClaimStatus(Long claimId, String zone, String newStatus) {
        Claim claim = claimRepository.findByIdAndZone(claimId, zone)
            .orElseThrow(() -> new RuntimeException("Claim not found in zone: " + zone));
        claim.setStatus(newStatus);
        return claimRepository.save(claim);
    }
}   
