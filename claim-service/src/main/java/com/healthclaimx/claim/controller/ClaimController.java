package com.healthclaimx.claim.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.healthclaimx.claim.entity.Claim;
import com.healthclaimx.claim.service.ClaimService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {
    
    private final ClaimService claimService;
    
    @PostMapping
    public ResponseEntity<Claim> createClaim(@RequestBody Claim claim) {
        Claim createdClaim = claimService.createClaim(claim);
        return ResponseEntity.ok(createdClaim);
    }
    
    @GetMapping
    public ResponseEntity<List<Claim>> getAllClaims() {
        List<Claim> claims = claimService.getALLClaims();
        return ResponseEntity.ok(claims);
    }
    
    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<Claim>> getClaimsByZone(@PathVariable String zone) {
        List<Claim> claims = claimService.getAllClaimsByZone(zone);
        return ResponseEntity.ok(claims);
    }
    
    @GetMapping("/{claimId}/zone/{zone}")
    public ResponseEntity<Claim> getClaimByIdAndZone(@PathVariable Long claimId, @PathVariable String zone) {
        Claim claim = claimService.getClaimByIdAndZone(claimId, zone)
            .orElseThrow(() -> new RuntimeException("Claim not found"));
        return ResponseEntity.ok(claim);
    }
    
    @GetMapping("/user/{userId}/zone/{zone}")
    public ResponseEntity<List<Claim>> getClaimsByUserAndZone(@PathVariable Long userId, @PathVariable String zone) {
        List<Claim> claims = claimService.getClaimsByUserIdAndZone(userId, zone);
        return ResponseEntity.ok(claims);
    }
    
    @GetMapping("/status/{status}/zone/{zone}")
    public ResponseEntity<List<Claim>> getClaimsByStatusAndZone(@PathVariable String status, @PathVariable String zone) {
        List<Claim> claims = claimService.getClaimsByStatusAndZone(status, zone);
        return ResponseEntity.ok(claims);
    }
    
    @PutMapping("/{claimId}/zone/{zone}/status/{status}")
    public ResponseEntity<Claim> updateClaimStatus(@PathVariable Long claimId, @PathVariable String zone, @PathVariable String status) {
        Claim updatedClaim = claimService.updateClaimStatus(claimId, zone, status);
        return ResponseEntity.ok(updatedClaim);
    }
}
