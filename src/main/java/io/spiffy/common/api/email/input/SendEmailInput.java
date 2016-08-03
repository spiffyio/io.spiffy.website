package io.spiffy.common.api.email.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.spiffy.common.api.email.dto.EmailProperties;
import io.spiffy.common.api.email.dto.EmailType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailInput {
    private EmailType type;
    private String address;
    private String idempotentId;
    private long accountId;
    private EmailProperties properties;
}
