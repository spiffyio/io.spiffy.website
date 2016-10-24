package io.spiffy.common.api.user.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SendRecoveryEmailInput extends APIInput {
    private String email;
}