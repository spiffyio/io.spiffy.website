package io.spiffy.common.api.email.input;

import lombok.*;

import io.spiffy.common.api.email.dto.EmailProperties;
import io.spiffy.common.api.email.dto.EmailType;
import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SendEmailInput extends APIInput {
    private EmailType type;
    private String address;
    private String idempotentId;
    private long accountId;
    private EmailProperties properties;
}
