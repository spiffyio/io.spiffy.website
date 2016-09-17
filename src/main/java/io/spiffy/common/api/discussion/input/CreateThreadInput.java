package io.spiffy.common.api.discussion.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.spiffy.common.api.discussion.dto.ThreadDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateThreadInput {
    private ThreadDTO thread;
    private List<String> participants;
}
