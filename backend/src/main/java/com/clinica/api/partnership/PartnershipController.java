package com.clinica.api.partnership;

import com.clinica.api.marketing.input.NewMarketingChannel;
import com.clinica.api.marketing.input.UpdateMarketingChannel;
import com.clinica.api.partnership.input.NewPartnership;
import com.clinica.api.partnership.input.UpdatePartnership;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@Validated
@RequestMapping("/api/v1/partnership")
public class PartnershipController {
    private final PartnershipService partnershipService;

    @Autowired
    public PartnershipController(PartnershipService partnershipService) {
        this.partnershipService = partnershipService;
    }

    @GetMapping
    public ResponseEntity<List<PartnershipDTO>> getMarketingChannels(Authentication authentication) {
        return ResponseEntity.ok().body(partnershipService.getAllPartnerships());
    }

    @PostMapping
    public ResponseEntity<Void> insertMarketingChannel(Authentication authentication,
                                                       @Valid @RequestBody NewPartnership newPartnership) {
        partnershipService.insertPartnership(newPartnership);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping
    public ResponseEntity<Void> updateMarketingChannel(Authentication authentication,
                                                       @Valid @RequestBody UpdatePartnership updatePartnership) {
        partnershipService.updateParthenship(updatePartnership);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
