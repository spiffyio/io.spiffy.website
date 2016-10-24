package io.spiffy.common.api.user.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;
import io.spiffy.common.dto.Account;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetAccountInput extends APIInput {
    private Account account;
}