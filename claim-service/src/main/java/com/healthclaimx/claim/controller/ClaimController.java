package com.healthclaimx.claim.controller;
import org.springframework.web.bind.annotation.*;
import com.healthclaimx.claim.entity.Claim;
import java.util.List;
@RestController
@RequestMapping("/claims")
public class ClaimController {
    
    @GetMapping
    public String test() {
        return "working fine!!";
    }
}