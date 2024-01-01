package com.clinica.api.service.servicedesc;

import com.clinica.api.service.servicedesc.input.NewServiceType;
import com.clinica.api.service.servicedesc.input.UpdateServiceType;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@Validated
@RequestMapping("/api/v1/servicedesc")
public class ServiceDescController {
    private final ServiceDescService serviceDescService;

    public ServiceDescController(ServiceDescService serviceDescService) {
        this.serviceDescService = serviceDescService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceDescDTO>> getServiceTypes(Authentication authentication) {
        return ResponseEntity.ok().body(serviceDescService.getAllServiceDescs());
    }

    @PostMapping
    public ResponseEntity<Void> insertServiceType(Authentication authentication,
                                                @Valid @RequestBody NewServiceType newServiceType) {
        serviceDescService.insertServiceDesc(newServiceType);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping
    public ResponseEntity<Void> updateServiceType(Authentication authentication,
                                                  @Valid @RequestBody UpdateServiceType updateServiceType) {
        serviceDescService.updateServiceDesc(updateServiceType);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
