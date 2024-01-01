package com.clinica.api.client;

import com.clinica.api.client.appointments.AppointmentDTO;
import com.clinica.api.client.appointments.AppointmentService;
import com.clinica.api.client.appointments.input.NewAppointmentInput;
import com.clinica.api.client.appointments.input.UpdateAppointment;
import com.clinica.api.client.inputs.ClientUpdate;
import com.clinica.api.client.inputs.NewClient;
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
@RequestMapping("/api/v1/client")
public class ClientController {
    private final ClientService clientService;
    private final AppointmentService appointmentService;

    @Autowired
    public ClientController(ClientService clientService, AppointmentService appointmentService) {
        this.clientService = clientService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(Authentication authentication, @PathVariable Integer id) {
        return ResponseEntity.ok().body(clientService.getClient(id));
    }
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getClients(Authentication authentication) {
        return ResponseEntity.ok().body(clientService.getAllClients());
    }

    @PostMapping
    public ResponseEntity<ClientDTO> insertClient(Authentication authentication, @Valid @RequestBody NewClient newClient) {
        ClientDTO client = clientService.insertClient(newClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }
    @PutMapping
    public ResponseEntity<Void> updateClient(Authentication authentication, @Valid @RequestBody ClientUpdate updatedClient) {
        clientService.updateClient(updatedClient);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/appointment")
    public ResponseEntity<List<AppointmentDTO>> getAppointments(Authentication authentication) {
        return ResponseEntity.ok().body(appointmentService.getAllAppointments());
    }

    @GetMapping("{id}/appointment")
    public ResponseEntity<List<AppointmentDTO>> getClientAppointments(Authentication authentication, @PathVariable Integer id) {
        return ResponseEntity.ok().body(appointmentService.getAllAppointmentsForClient(id));
    }

    @PostMapping("/appointment")
    public ResponseEntity<Void> insertAppointment(Authentication authentication,
                                                       @Valid @RequestBody NewAppointmentInput newAppointmentInput) {
        appointmentService.insertAppointment(newAppointmentInput);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/appointment")
    public ResponseEntity<Void> updateAppointment(Authentication authentication,
                                                  @Valid @RequestBody UpdateAppointment updateAppointment) {
        appointmentService.updateAppointment(updateAppointment);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
