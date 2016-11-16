package io.spiffy.common.api.security.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetTokenizedStringInput extends APIInput {
    private String token;
}
