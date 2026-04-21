package com.healthclaimx.claim.event;
import org.springframework.context.ApplicationEvent;
import com.healthclaimx.claim.entity.Claim;
public class ClaimCreatedEvent extends ApplicationEvent {
    private Long claimId;
    public ClaimCreatedEvent(Long claimId) {
        super(source);
        this.claimId = claim.getId();
    }
    public Long getClaimId() 
    {
        return claimId;    
    }
}   