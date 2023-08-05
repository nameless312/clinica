package com.clinica.api.marketing.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewMarketingChannel(
        @NotNull(message = "User id must be set")
        Integer userId,
        @NotEmpty(message = "Channel cannot be blank")
        String channel
) {
}
