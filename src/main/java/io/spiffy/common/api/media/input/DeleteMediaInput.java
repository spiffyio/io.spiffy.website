package io.spiffy.common.api.media.input;

import lombok.*;

import java.util.List;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeleteMediaInput extends APIInput {
    private Long accountId;
    private List<String> names;
}
