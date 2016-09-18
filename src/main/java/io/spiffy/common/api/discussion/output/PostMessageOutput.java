package io.spiffy.common.api.discussion.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.spiffy.common.api.discussion.dto.MessengerMessage;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostMessageOutput {
    private MessengerMessage message;
}
