package com.clinica.api.Procedure;

import com.clinica.api.exceptions.ResourceNotFoundException;
import com.clinica.api.procedure.Procedure;
import com.clinica.api.procedure.ProcedureDAO;
import com.clinica.api.procedure.ProcedureDTO;
import com.clinica.api.procedure.ProcedureService;
import com.clinica.api.procedure.input.NewProcedure;
import com.clinica.api.procedure.input.UpdateProcedure;
import com.clinica.api.user.User;
import com.clinica.api.user.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcedureServiceTest {
    @Mock
    private UserDAO userDAO;
    @Mock
    private ProcedureDAO procedureDAO;
    @Mock
    private Clock clock;
    private ProcedureService underTest;

    @BeforeEach
    void setUp() {
        clock = Clock.systemDefaultZone();
        underTest = new ProcedureService(procedureDAO, userDAO, clock);
    }
    @Test
    void selectAllProceduresEmpty() {
        // Given
        List<com.clinica.api.procedure.Procedure> procedures = List.of();

        // When
        when(procedureDAO.selectProcedures()).thenReturn(procedures);

        // Then
        assertThat(underTest.getAllProcedures()).isEmpty();
    }
    @Test
    void selectAllProceduresEmptyNotEmpty() {
        // Given
        int id = 10;
        com.clinica.api.procedure.Procedure procedure = com.clinica.api.procedure.Procedure.builder()
                .procedureId(id)
                .dtAdded(Timestamp.from(Instant.ofEpochMilli(10000000)))
                .dtUpdate(Timestamp.from(Instant.ofEpochMilli(10000000)))
                .procedureDesc("This and that")
                .build();

        // When
        when(procedureDAO.selectProcedures()).thenReturn(List.of(procedure));

        // Then
        List<ProcedureDTO> channels = underTest.getAllProcedures();
        assertThat(channels).isNotEmpty();
        assertThat(channels.get(0).procedureID()).isEqualTo(procedure.getProcedureId());
    }

    @Test
    void willThrowWhenInsertProcedureHasNonExistingUserId() {
        // Given
        int id = 10;
        NewProcedure newProcedure =
                new NewProcedure(id, "This and That");

        // When
        // Then
        assertThatThrownBy(() -> underTest.insertProcedure(newProcedure))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("user with id [%s] not found".formatted(id));
    }

    @Test
    void insertProcedureWithAllOptions() {
        // Given
        User user = new User();
        user.setUserId(1);

        NewProcedure request =
                new NewProcedure(1, "This and That");

        // When
        when(userDAO.selectUserById(request.userId())).thenReturn(Optional.of(user));
        underTest.insertProcedure(request);

        // Then
        ArgumentCaptor<Procedure> marketingArgumentCaptor = ArgumentCaptor.forClass(
                Procedure.class
        );

        verify(procedureDAO).insertProcedure(marketingArgumentCaptor.capture());

        Procedure capturedPartnership = marketingArgumentCaptor.getValue();

        assertThat(capturedPartnership.getUser().getUserId()).isEqualTo(request.userId());
        assertThat(capturedPartnership.getProcedureDesc()).isEqualTo(request.procedureDesc());
    }

    @Test
    void updateProcedureWillThrowOnNonExistingProcedureId(){
        // Given
        int id = 10;
        UpdateProcedure updateProcedure = new UpdateProcedure(id, "This and that");

        // When
        // Then
        assertThatThrownBy(() -> underTest.updateProcedure(updateProcedure))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("procedure with id [%s] not found".formatted(id));
    }

    @Test
    void updateProcedureWillSucceed() {
        // Given
        int id = 1;
        String procedureDesc = "This and That";
        UpdateProcedure request = new UpdateProcedure(id, procedureDesc);

        Procedure procedure = new Procedure();
        procedure.setProcedureId(1);
        procedure.setProcedureDesc("test");

        // When
        when(procedureDAO.selectProcedureById(id)).thenReturn(Optional.of(procedure));

        // Call the method to be tested
        underTest.updateProcedure(request);

        // Then
        ArgumentCaptor<Procedure> procedureArgumentCaptor = ArgumentCaptor.forClass(Procedure.class);
        verify(procedureDAO).updateProcedure(procedureArgumentCaptor.capture());

        Procedure capturedChannel = procedureArgumentCaptor.getValue();

        assertThat(capturedChannel.getProcedureDesc()).isEqualTo(procedureDesc);
        assertThat(capturedChannel.getDtUpdate()).isAfterOrEqualTo(procedure.getDtUpdate());
    }
}