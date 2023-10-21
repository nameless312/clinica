package com.clinica.api.client.appointments;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("AppointmentJPA")
public class AppointmentAccessJPADataAccessService implements AppointmentDAO {

    private final AppointmentRepository appointmentRepository;

    public AppointmentAccessJPADataAccessService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;

    }
    @Override
    public Optional<Appointment> selectAppointmentById(Integer id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public List<Appointment> selectAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> selectAppointmentsForClient(Integer clientId) {
        return appointmentRepository.findByClientId(clientId);
    }

    @Override
    public Appointment insertAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }
}
