package io.spiffy.common.api.discussion.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.spiffy.common.api.discussion.dto.MessengerMessage;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMessagesOutput {
    private List<MessengerMessage> messages;
}
