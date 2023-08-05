package com.clinica.api.marketing;

import com.clinica.api.client.ClientDTO;
import com.clinica.api.client.ClientService;
import com.clinica.api.client.inputs.NewClient;
import com.clinica.api.marketing.input.NewMarketingChannel;
import com.clinica.api.marketing.input.UpdateMarketingChannel;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.sql.Update;
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
@RequestMapping("/api/v1/marketing")
public class MarketingController {
    private final MarketingService marketingService;

    @Autowired
    public MarketingController(MarketingService marketingService) {
        this.marketingService = marketingService;
    }

    @GetMapping
    public ResponseEntity<List<MarketingDTO>> getMarketingChannels(Authentication authentication) {
        return ResponseEntity.ok().body(marketingService.getAllMarketingChannels());
    }

    @PostMapping
    public ResponseEntity<Void> insertMarketingChannel(Authentication authentication,
                                                       @Valid @RequestBody NewMarketingChannel newChannel) {
        marketingService.insertMarketingChannel(newChannel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping
    public ResponseEntity<Void> updateMarketingChannel(Authentication authentication,
                                                       @Valid @RequestBody UpdateMarketingChannel updatedClient) {
        marketingService.updateMarketingChannel(updatedClient);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
