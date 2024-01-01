package com.clinica.api.service.servicetype;

import com.clinica.api.service.servicetype.input.NewServiceType;
import com.clinica.api.service.servicetype.input.UpdateServiceType;
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
@RequestMapping("/api/v1/servicetype")
public class ServiceTypeController {
    private final ServiceTypeService serviceTypeService;

    public ServiceTypeController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceTypeDTO>> getServiceTypes(Authentication authentication) {
        return ResponseEntity.ok().body(serviceTypeService.getAllServiceTypes());
    }

    @PostMapping
    public ResponseEntity<Void> insertServiceType(Authentication authentication,
                                                @Valid @RequestBody NewServiceType newServiceType) {
        serviceTypeService.insertServiceType(newServiceType);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping
    public ResponseEntity<Void> updateServiceType(Authentication authentication,
                                                  @Valid @RequestBody UpdateServiceType updateServiceType) {
        serviceTypeService.updateServiceType(updateServiceType);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
