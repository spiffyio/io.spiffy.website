package io.spiffy.common.api.email.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostEmailAddressInput extends APIInput {
    private String address;
}
