package io.spiffy.common.api.output;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostOutput extends APIOutput {
    private static final long serialVersionUID = -2166031246273613708L;
    private Long id;
}
