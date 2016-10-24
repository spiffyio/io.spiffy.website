package io.spiffy.common.api.user.output;

import lombok.*;

import io.spiffy.common.api.output.APIOutput;
import io.spiffy.common.dto.Account;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetAccountOutput extends APIOutput {
    private static final long serialVersionUID = 1693869205246279741L;
    private Account account;
}
