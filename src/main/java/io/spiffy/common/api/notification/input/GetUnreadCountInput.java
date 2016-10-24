package io.spiffy.common.api.notification.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetUnreadCountInput extends APIInput {
    private Long accountId;
}
