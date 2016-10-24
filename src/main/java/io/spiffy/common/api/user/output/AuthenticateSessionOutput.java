package io.spiffy.common.api.user.output;

import lombok.*;

import io.spiffy.common.api.output.APIOutput;
import io.spiffy.common.dto.Account;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthenticateSessionOutput extends APIOutput {
    private static final long serialVersionUID = -7451324043166486319L;
    private Account account;
}
