package io.spiffy.common.api.user.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SendVerifyEmailInput extends APIInput {
    private Long accountId;
    private String email;
    private String idempotentId;
}