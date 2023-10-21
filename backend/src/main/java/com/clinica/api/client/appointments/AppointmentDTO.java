package com.clinica.api.client.appointments;

import com.clinica.api.client.ClientDTO;
import com.clinica.api.service.servicetype.ServiceTypeDTO;

import java.sql.Timestamp;

public record AppointmentDTO(
        Integer appointmentID,
        ClientDTO client,
        ServiceTypeDTO serviceType,
        Timestamp dtAppointment
) {
}
