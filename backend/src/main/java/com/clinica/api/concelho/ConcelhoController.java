package com.clinica.api.concelho;

import com.clinica.api.marketing.MarketingDTO;
import com.clinica.api.marketing.MarketingService;
import com.clinica.api.marketing.input.NewMarketingChannel;
import com.clinica.api.marketing.input.UpdateMarketingChannel;
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
@RequestMapping("/api/v1/concelho")
public class ConcelhoController {
    private final ConcelhoService concelhoService;

    @Autowired
    public ConcelhoController(ConcelhoService concelhoService) {
        this.concelhoService = concelhoService;
    }

    @GetMapping
    public ResponseEntity<List<ConcelhoDTO>> getAllConcelhos(Authentication authentication) {
        return ResponseEntity.ok().body(concelhoService.getAllConcelhos());
    }
}
