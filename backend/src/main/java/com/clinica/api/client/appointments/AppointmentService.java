package com.clinica.api.client.appointments;

import com.clinica.api.client.appointments.input.NewAppointmentInput;
import com.clinica.api.client.appointments.input.UpdateAppointment;
import com.clinica.api.client.Client;
import com.clinica.api.client.ClientDAO;
import com.clinica.api.exceptions.ResourceNotFoundException;
import com.clinica.api.service.servicetype.ServiceType;
import com.clinica.api.service.servicetype.ServiceTypeDAO;
import com.clinica.api.user.User;
import com.clinica.api.user.UserDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentService {
    private final AppointmentDAO appointmentDAO;
    private final UserDAO userDAO;
    private final ClientDAO clientDAO;
    private final ServiceTypeDAO serviceTypeDAO;
    private final Clock clock;

    @Autowired
    public AppointmentService(@Qualifier(value = "AppointmentJPA") AppointmentDAO appointmentDAO,
                              @Qualifier(value = "UserJPA") UserDAO userDAO,
                              ClientDAO clientDAO, ServiceTypeDAO serviceTypeDAO, Clock clock) {
        this.appointmentDAO = appointmentDAO;
        this.userDAO = userDAO;
        this.clientDAO = clientDAO;
        this.serviceTypeDAO = serviceTypeDAO;
        this.clock = clock;
    }

    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentDAO.selectAppointments();
        if (appointments.isEmpty()) {
            return new ArrayList<>();
        }
        return appointments.stream().map(Appointment::toDTO).collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAllAppointmentsForClient(Integer clientId) {
        List<Appointment> appointments = appointmentDAO.selectAppointmentsForClient(clientId);
        if (appointments.isEmpty()) {
            return new ArrayList<>();
        }
        return appointments.stream().map(Appointment::toDTO).collect(Collectors.toList());
    }

    public void insertAppointment(NewAppointmentInput newAppointmentInput) {
        Integer userId = newAppointmentInput.userId();
        Integer clientId = newAppointmentInput.clientId();
        Integer serviceTypeId = newAppointmentInput.serviceTypeId();

        User user = userDAO.selectUserById(userId).
                orElseThrow(
                        () -> new ResourceNotFoundException("user with id [%s] not found".formatted(userId))
                );

        Client client = clientDAO.selectClientById(clientId).
                orElseThrow(
                        () -> new ResourceNotFoundException("client with id [%s] not found".formatted(clientId))
                );

        ServiceType serviceType = serviceTypeDAO.selectServiceTypeById(serviceTypeId).
                orElseThrow(
                        () -> new ResourceNotFoundException("service type with id [%s] not found".formatted(serviceTypeId))
                );

        Appointment appointment = new Appointment();

        appointment.setUser(user);
        appointment.setClient(client);
        appointment.setServiceType(serviceType);
        appointment.setDtAppointment(newAppointmentInput.dtAppointment());
        appointmentDAO.insertAppointment(appointment);
    }

    public void updateAppointment(UpdateAppointment updateAppointment) {
        Integer appointmentId = updateAppointment.appointmentId();
        Integer clientId = updateAppointment.clientId();
        Integer serviceTypeId = updateAppointment.serviceTypeId();

        Appointment appointment = appointmentDAO.selectAppointmentById(appointmentId).
                orElseThrow(
                        () -> new ResourceNotFoundException("appointment with id [%s] not found".formatted(appointmentId))
                );

        Client client = clientDAO.selectClientById(clientId).
                orElseThrow(
                        () -> new ResourceNotFoundException("client with id [%s] not found".formatted(clientId))
                );

        ServiceType serviceType = serviceTypeDAO.selectServiceTypeById(serviceTypeId).
                orElseThrow(
                        () -> new ResourceNotFoundException("service type with id [%s] not found".formatted(serviceTypeId))
                );

        appointment.setClient(client);
        appointment.setServiceType(serviceType);
        appointment.setDtAppointment(updateAppointment.dtAppointment());
        appointment.setDtUpdate(new Timestamp(Instant.now(clock).toEpochMilli()));

        appointmentDAO.updateAppointment(appointment);
    }
}
