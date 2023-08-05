package com.clinica.api.marketing;

import com.clinica.api.testcontainers.AbstractTestcontainers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MarketingJPADataAccessServiceTest extends AbstractTestcontainers {
    private MarketingAccessJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private MarketingRepository marketingRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new MarketingAccessJPADataAccessService(marketingRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllMarketingChannels() {
        // When
        underTest.selectMarketingChannels();

        // Then
        verify(marketingRepository).findAll();
    }

    @Test
    void selectMarketingChannelById() {
        // Given
        int id = 1;

        // When
        underTest.selectMarketingChannelById(id);

        // Then
        verify(marketingRepository).findById(id);
    }

    @Test
    void insertMarketingChannel() {
        // Given
        Marketing marketing = new Marketing();

        // When
        underTest.insertMarketingChannel(marketing);

        // Then
        verify(marketingRepository).save(marketing);
    }
    @Test
    void updateMarketingChannel() {
        // Given
        Marketing marketing = new Marketing();

        // When
        underTest.updateMarketingChannel(marketing);

        // Then
        verify(marketingRepository).save(marketing);
    }
}