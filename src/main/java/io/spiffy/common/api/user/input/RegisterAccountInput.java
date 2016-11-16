package io.spiffy.common.api.user.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;
import io.spiffy.common.api.user.dto.Credentials;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RegisterAccountInput extends APIInput {
    private String username;
    private String email;
    private Credentials credentials;
}
