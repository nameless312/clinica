package com.clinica.api.client.appointments.input;

import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record NewAppointmentInput(
        @NotNull(message = "User id must be set")
        Integer userId,

        @NotNull(message = "Client id must be set")
        Integer clientId,

        @NotNull(message = "Service type id must be set")
        Integer serviceTypeId,
        @NotNull(message = "Appointment date cannot be blank")
        Timestamp dtAppointment
) {
}
