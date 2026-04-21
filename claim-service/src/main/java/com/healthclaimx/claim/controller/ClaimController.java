package com.healthclaimx.claim.controller;
import org.springframework.web.bind.annotation.*;
import com.healthclaimx.claim.entity.Claim;
import java.util.List;
@RestController
@RequestMapping("/claims")
public class ClaimController {
    @GetMapping
    public List<Claim> getAllClaims() {
        // Logic to retrieve all claims
        return null; // Placeholder
    }
     @GetMapping
    public String test() {
        // Logic to retrieve all claims
        return "Claim Service Running"; // Placeholder
    }
}