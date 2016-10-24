package io.spiffy.common.api.user.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostAccountInput extends APIInput {
    private String userName;
    private String emailAddress;
    private Long iconId;
    private Long bannerId;
}