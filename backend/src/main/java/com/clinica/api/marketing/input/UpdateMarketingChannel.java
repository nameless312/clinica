package com.clinica.api.marketing.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateMarketingChannel(
        @NotNull(message = "Marketing id must be set")
        Integer marketingId,
        @NotEmpty(message = "Channel cannot be blank")
        String channel
) {
}
