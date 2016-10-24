package io.spiffy.common.api.discussion.output;

import lombok.*;

import java.util.List;

import io.spiffy.common.api.discussion.dto.MessengerMessage;
import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetMessagesOutput extends APIOutput {
    private static final long serialVersionUID = 289259274623625165L;
    private List<MessengerMessage> messages;
}
