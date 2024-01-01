package com.clinica.api.procedure;

import com.clinica.api.testcontainers.AbstractTestcontainers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProcedureJPADataAccessServiceTest extends AbstractTestcontainers {
    private ProcedureAccessJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private ProcedureRepository procedureRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ProcedureAccessJPADataAccessService(procedureRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllProcedures() {
        // When
        underTest.selectProcedures();

        // Then
        verify(procedureRepository).findAll();
    }

    @Test
    void selectProcedureById() {
        // Given
        int id = 1;

        // When
        underTest.selectProcedureById(id);

        // Then
        verify(procedureRepository).findById(id);
    }

    @Test
    void insertProcedure() {
        // Given
        Procedure procedure = new Procedure();

        // When
        underTest.insertProcedure(procedure);

        // Then
        verify(procedureRepository).save(procedure);
    }
    @Test
    void updateProcedure() {
        // Given
        Procedure procedure = new Procedure();

        // When
        underTest.updateProcedure(procedure);

        // Then
        verify(procedureRepository).save(procedure);
    }
}