package com.clinica.api.marketing;

import com.clinica.api.exceptions.ResourceNotFoundException;
import com.clinica.api.marketing.input.NewMarketingChannel;
import com.clinica.api.marketing.input.UpdateMarketingChannel;
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
public class MarketingService {
    private final MarketingDAO marketingDAO;
    private final UserDAO userDAO;
    private final Clock clock;

    @Autowired
    public MarketingService(@Qualifier(value = "MarketingJPA") MarketingDAO marketingDAO,
                            @Qualifier(value = "UserJPA") UserDAO userDAO,
                            Clock clock) {
        this.marketingDAO = marketingDAO;
        this.userDAO = userDAO;
        this.clock = clock;
    }

    public List<MarketingDTO> getAllMarketingChannels() {
        List<Marketing> channels = marketingDAO.selectMarketingChannels();
        if (channels.isEmpty()) {
            return new ArrayList<>();
        }
        return channels.stream().map(Marketing::toDTO).collect(Collectors.toList());
    }

    public void insertMarketingChannel(NewMarketingChannel newChannel) {
        Integer userId = newChannel.userId();

        User user = userDAO.selectUserById(userId).
                orElseThrow(
                        () -> new ResourceNotFoundException("user with id [%s] not found".formatted(userId))
                );

        Marketing marketing = new Marketing();

        marketing.setUser(user);
        marketing.setChannel(newChannel.channel());

        marketingDAO.insertMarketingChannel(marketing);
    }

    public void updateMarketingChannel(UpdateMarketingChannel updatedChannel) {
        Integer marketingId = updatedChannel.marketingId();

        Marketing channel = marketingDAO.selectMarketingChannelById(marketingId).
                orElseThrow(
                        () -> new ResourceNotFoundException("marketing with id [%s] not found".formatted(marketingId))
                );

        if (channel.getChannel() == null || !channel.getChannel().equals(updatedChannel.channel())) {
            channel.setChannel(updatedChannel.channel());
            channel.setDtUpdate(new Timestamp(Instant.now(clock).toEpochMilli()));
            marketingDAO.updateMarketingChannel(channel);
        }
    }
}
