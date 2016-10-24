package io.spiffy.common.api.media.output;

import lombok.*;

import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostMediaOutput extends APIOutput {
    private static final long serialVersionUID = 5132170659160068796L;
    private String name;
}
