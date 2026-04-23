package com.healthclaimx.claim.listener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.healthclaimx.claim.event.ClaimCreatedEvent;
@Component
public class ClaimEventListener {
    @EventListener
    public void handleClaimCreatedEvent(ClaimCreatedEvent event) {
        Long claimId = event.getClaimId();
        System.out.println("Received ClaimCreatedEvent for claim ID: " + event.getClaimId());  
    }
}   