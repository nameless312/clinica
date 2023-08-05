package com.clinica.api.Partnership;

import com.clinica.api.marketing.Marketing;
import com.clinica.api.partnership.Partnership;
import com.clinica.api.partnership.PartnershipAccessJPADataAccessService;
import com.clinica.api.partnership.PartnershipRepository;
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
public class PartnershipJPADataAccessServiceTest extends AbstractTestcontainers {
    private PartnershipAccessJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private PartnershipRepository partnershipRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new PartnershipAccessJPADataAccessService(partnershipRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllPartnerships() {
        // When
        underTest.selectPartnerships();

        // Then
        verify(partnershipRepository).findAll();
    }

    @Test
    void selectPartnershipById() {
        // Given
        int id = 1;

        // When
        underTest.selectPartnershipById(id);

        // Then
        verify(partnershipRepository).findById(id);
    }

    @Test
    void insertPartnership() {
        // Given
        Partnership partnership = new Partnership();

        // When
        underTest.insertPartnership(partnership);

        // Then
        verify(partnershipRepository).save(partnership);
    }
    @Test
    void updateMarketingChannel() {
        // Given
        Partnership partnership = new Partnership();

        // When
        underTest.updatePartnership(partnership);

        // Then
        verify(partnershipRepository).save(partnership);
    }
}