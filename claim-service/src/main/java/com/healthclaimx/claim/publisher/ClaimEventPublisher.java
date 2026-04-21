package com.healthclaimx.claim.publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import com.healthclaimx.claim.event.ClaimCreatedEvent;
@Component
public class ClaimEventPublisher {
    @Autowired
    private final ApplicationEventPublisher publisher;
    public ClaimEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
    public void publishClaimCreatedEvent(Long claimId) {
        ClaimCreatedEvent event = new ClaimCreatedEvent(claimId);
        publisher.publishEvent(event);                                          
}
}