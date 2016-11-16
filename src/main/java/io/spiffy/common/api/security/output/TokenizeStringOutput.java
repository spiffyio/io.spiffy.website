package io.spiffy.common.api.security.output;

import lombok.*;

import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TokenizeStringOutput extends APIOutput {
    private static final long serialVersionUID = -8611496277216217025L;
    private String token;
}
