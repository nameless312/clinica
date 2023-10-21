package com.clinica.api.client.appointments;

import java.util.List;
import java.util.Optional;

public interface AppointmentDAO {
    Optional<Appointment> selectAppointmentById(Integer id);
    List<Appointment> selectAppointments();
    List<Appointment> selectAppointmentsForClient(Integer clientId);
    Appointment insertAppointment(Appointment appointment);
    void updateAppointment(Appointment appointment);
}
