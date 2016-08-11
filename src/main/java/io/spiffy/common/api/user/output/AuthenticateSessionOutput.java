package io.spiffy.common.api.user.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.spiffy.common.dto.Account;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateSessionOutput {
    private Account account;
}
