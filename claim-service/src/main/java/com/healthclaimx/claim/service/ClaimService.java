package com.healthclaimx.claim.service;
import java.util.List;

import org.springframework.stereotype.Service;
import com.healthclaimx.claim.repository.ClaimRepository;
@Service
public class ClaimService {
    private final ClaimRepository claimRepository;
    public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }
    public Claim createClaim(Claim claim) {
        claim.setStatus("SUBMITTED");
        Claim savedClaim = claimRepository.save(claim);
        eventPublisher.publishClaimCreatedEvent(savedClaim.getId());
        return savedClaim;    
    }
    public List<Claim> getALLClaims() {
        return claimRepository.findAll();
    }
}   
