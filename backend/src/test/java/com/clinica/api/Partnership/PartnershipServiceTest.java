package com.clinica.api.Partnership;

import com.clinica.api.exceptions.ResourceNotFoundException;
import com.clinica.api.partnership.Partnership;
import com.clinica.api.partnership.PartnershipDAO;
import com.clinica.api.partnership.PartnershipDTO;
import com.clinica.api.partnership.PartnershipService;
import com.clinica.api.partnership.input.NewPartnership;
import com.clinica.api.partnership.input.UpdatePartnership;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartnershipServiceTest {
    @Mock
    private UserDAO userDAO;
    @Mock
    private PartnershipDAO partnershipDAO;
    @Mock
    private Clock clock;
    private PartnershipService underTest;

    @BeforeEach
    void setUp() {
        clock = Clock.systemDefaultZone();
        underTest = new PartnershipService(partnershipDAO, userDAO, clock);
    }
    @Test
    void selectAllPartnershipsEmpty() {
        // Given
        List<Partnership> partnerships = List.of();

        // When
        when(partnershipDAO.selectPartnerships()).thenReturn(partnerships);

        // Then
        assertThat(underTest.getAllPartnerships()).isEmpty();
    }
    @Test
    void selectAllPartnershipsEmptyNotEmpty() {
        // Given
        int id = 10;
        Partnership partnership = Partnership.builder()
                .partnershipId(id)
                .dtAdded(Timestamp.from(Instant.ofEpochMilli(10000000)))
                .dtUpdate(Timestamp.from(Instant.ofEpochMilli(10000000)))
                .partner("John Doe")
                .mobile("910223445")
                .locality("Porto")
                .commision(10)
                .job("Hair Dresser")
                .build();

        // When
        when(partnershipDAO.selectPartnerships()).thenReturn(List.of(partnership));

        // Then
        List<PartnershipDTO> channels = underTest.getAllPartnerships();
        assertThat(channels).isNotEmpty();
        assertThat(channels.get(0).partnershipID()).isEqualTo(partnership.getPartnershipId());
    }

    @Test
    void willThrowWhenInsertPartnershipHasNonExistingUserId() {
        // Given
        int id = 10;
        NewPartnership newPartnership =
                new NewPartnership(id, "John doe", "locality", "909898878", 40, "Hair Dresser");

        // When
        // Then
        assertThatThrownBy(() -> underTest.insertPartnership(newPartnership))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("user with id [%s] not found".formatted(id));
    }

    @Test
    void insertPartnershipWithAllOptions() {
        // Given
        User user = new User();
        user.setUserId(1);

        NewPartnership request =
                new NewPartnership(1, "John doe", "locality", "909898878", 40, "Hair Dresser");

        // When
        when(userDAO.selectUserById(request.userId())).thenReturn(Optional.of(user));
        underTest.insertPartnership(request);

        // Then
        ArgumentCaptor<Partnership> marketingArgumentCaptor = ArgumentCaptor.forClass(
                Partnership.class
        );

        verify(partnershipDAO).insertPartnership(marketingArgumentCaptor.capture());

        Partnership capturedPartnership = marketingArgumentCaptor.getValue();

        assertThat(capturedPartnership.getUser().getUserId()).isEqualTo(request.userId());
        assertThat(capturedPartnership.getPartner()).isEqualTo(request.partner());
        assertThat(capturedPartnership.getJob()).isEqualTo(request.job());
        assertThat(capturedPartnership.getLocality()).isEqualTo(request.locality());
        assertThat(capturedPartnership.getCommision()).isEqualTo(request.commission());
        assertThat(capturedPartnership.getMobile()).isEqualTo(request.mobile());
    }

    @Test
    void updatePartnershipWillThrowOnNonExistingPartnershipId(){
        // Given
        int id = 10;
        UpdatePartnership updatePartnership = new UpdatePartnership(id, "John doe", "locality", "909898878", 40, "Hair Dresser");

        // When
        // Then
        assertThatThrownBy(() -> underTest.updatePartnership(updatePartnership))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("partnership with id [%s] not found".formatted(id));
    }

    @Test
    void updatePartnershipWillSucceed() {
        // Given
        int id = 1;
        String partner = "John Doe";
        String locality = "Porto";
        String job = "Hair Dresser";
        String mobile = "910999999";
        Integer commission = 40;
        UpdatePartnership request = new UpdatePartnership(id, partner, locality, mobile, commission, job);

        Partnership partnership = new Partnership();
        partnership.setPartnershipId(1);
        partnership.setPartner("test partner");
        partnership.setLocality("test locality");
        partnership.setJob("test job");
        partnership.setMobile("test mobile");
        partnership.setCommision(0);

        // When
        when(partnershipDAO.selectPartnershipById(id)).thenReturn(Optional.of(partnership));

        // Call the method to be tested
        underTest.updatePartnership(request);

        // Then
        ArgumentCaptor<Partnership> partnershipArgumentCaptor = ArgumentCaptor.forClass(Partnership.class);
        verify(partnershipDAO).updatePartnership(partnershipArgumentCaptor.capture());

        Partnership capturedChannel = partnershipArgumentCaptor.getValue();

        assertThat(capturedChannel.getPartner()).isEqualTo(partner);
        assertThat(capturedChannel.getLocality()).isEqualTo(locality);
        assertThat(capturedChannel.getJob()).isEqualTo(job);
        assertThat(capturedChannel.getMobile()).isEqualTo(mobile);
        assertThat(capturedChannel.getCommision()).isEqualTo(commission);
        assertThat(capturedChannel.getDtUpdate()).isAfterOrEqualTo(partnership.getDtUpdate());
    }
}