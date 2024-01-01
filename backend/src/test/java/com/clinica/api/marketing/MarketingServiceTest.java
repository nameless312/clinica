package com.clinica.api.marketing;

import com.clinica.api.exceptions.ResourceNotFoundException;
import com.clinica.api.marketing.input.NewMarketingChannel;
import com.clinica.api.marketing.input.UpdateMarketingChannel;
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
class MarketingServiceTest {
    @Mock
    private UserDAO userDAO;
    @Mock
    private MarketingDAO marketingDAO;
    @Mock
    private Clock clock;
    private MarketingService underTest;

    @BeforeEach
    void setUp() {
        clock = Clock.systemDefaultZone();
        underTest = new MarketingService(marketingDAO, userDAO, clock);
    }
    @Test
    void selectAllMarketingChannelsEmpty() {
        // Given
        List<Marketing> channels = List.of();

        // When
        when(marketingDAO.selectMarketingChannels()).thenReturn(channels);

        // Then
        assertThat(underTest.getAllMarketingChannels()).isEmpty();
    }
    @Test
    void selectAllMarketingChannelsNotEmpty() {
        // Given
        int id = 10;
        Marketing channel = Marketing.builder()
                .marketingId(id)
                .dtAdded(Timestamp.from(Instant.ofEpochMilli(10000000)))
                .dtUpdate(Timestamp.from(Instant.ofEpochMilli(10000000)))
                .channel("Facebook")
                .build();

        // When
        when(marketingDAO.selectMarketingChannels()).thenReturn(List.of(channel));

        // Then
        List<MarketingDTO> channels = underTest.getAllMarketingChannels();
        assertThat(channels).isNotEmpty();
        assertThat(channels.get(0).marketingID()).isEqualTo(channel.getMarketingId());
    }

    @Test
    void willThrowWhenInsertMarketingChannelHasNonExistingUserId() {
        // Given
        int id = 10;
        NewMarketingChannel newChannel = new NewMarketingChannel(id, "Facebook");

        // When
        // Then
        assertThatThrownBy(() -> underTest.insertMarketingChannel(newChannel))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("user with id [%s] not found".formatted(id));
    }

    @Test
    void insertMarketingChannelWithAllOptions() {
        // Given
        User user = new User();
        user.setUserId(1);

        NewMarketingChannel request = new NewMarketingChannel(user.getUserId(), "Facebook");

        // When
        when(userDAO.selectUserById(request.userId())).thenReturn(Optional.of(user));
        underTest.insertMarketingChannel(request);

        // Then
        ArgumentCaptor<Marketing> marketingArgumentCaptor = ArgumentCaptor.forClass(
                Marketing.class
        );

        verify(marketingDAO).insertMarketingChannel(marketingArgumentCaptor.capture());

        Marketing capturedChannel = marketingArgumentCaptor.getValue();

        assertThat(capturedChannel.getUser().getUserId()).isEqualTo(request.userId());
        assertThat(capturedChannel.getChannel()).isNotEmpty();
        assertThat(capturedChannel.getChannel()).isEqualTo(request.channel());
    }

    @Test
    void updateMarketingChannelWillThrowOnNonExistingMarketingChannelId(){
        // Given
        int id = 10;
        UpdateMarketingChannel updatedChannel = new UpdateMarketingChannel(id, "Facebook");

        // When
        // Then
        assertThatThrownBy(() -> underTest.updateMarketingChannel(updatedChannel))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("marketing with id [%s] not found".formatted(id));
    }

    @Test
    void updateMarketingChannelWillNotUpdateOnSameChannel(){
        // Given
        int id = 1;
        String channel = "Facebook";
        UpdateMarketingChannel updatedChannel = new UpdateMarketingChannel(id, channel);

        Marketing marketingChannel = new Marketing();
        marketingChannel.setMarketingId(1);
        marketingChannel.setChannel(channel);

        // When
        when(marketingDAO.selectMarketingChannelById(id)).thenReturn(Optional.of(marketingChannel));

        // Then
        underTest.updateMarketingChannel(updatedChannel);
        verify(marketingDAO, never()).updateMarketingChannel(any(Marketing.class));
    }

    @Test
    void updateMarketingChannelWillSucceed() {
        // Given
        int id = 1;
        String channel = "Facebook";
        UpdateMarketingChannel request = new UpdateMarketingChannel(id, channel);

        Marketing marketingChannel = new Marketing();
        marketingChannel.setMarketingId(1);
        marketingChannel.setChannel("test channel");

        // When
        when(marketingDAO.selectMarketingChannelById(id)).thenReturn(Optional.of(marketingChannel));

        // Call the method to be tested
        underTest.updateMarketingChannel(request);

        // Then
        ArgumentCaptor<Marketing> marketingArgumentCaptor = ArgumentCaptor.forClass(Marketing.class);
        verify(marketingDAO).updateMarketingChannel(marketingArgumentCaptor.capture());

        Marketing capturedChannel = marketingArgumentCaptor.getValue();

        assertThat(capturedChannel.getChannel()).isEqualTo(channel);
        assertThat(capturedChannel.getDtUpdate()).isAfterOrEqualTo(marketingChannel.getDtUpdate());
    }
}