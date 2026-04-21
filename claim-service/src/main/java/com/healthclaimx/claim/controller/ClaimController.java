package com.healthclaimx.claim.controller;
import org.springframework.web.bind.annotation.*;
import com.healthclaimx.claim.entity.Claim;
import java.util.List;
@RestController
@RequestMapping("/claims")
public class ClaimController {
    
    private final ClaimService claimService;
    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }
    @PostMapping
    public Claim createClaim(@RequestBody Claim claim) {
        return claimService.createClaim(claim);
    }
    @GetMapping
    public List<Claim> getAllClaims() {
        return claimService.getALLClaims();
    }           

}
