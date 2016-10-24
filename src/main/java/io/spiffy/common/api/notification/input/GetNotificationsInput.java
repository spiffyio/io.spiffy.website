package io.spiffy.common.api.notification.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetNotificationsInput extends APIInput {
    private Long accountId;
}
