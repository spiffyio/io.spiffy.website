package io.spiffy.common.api.output;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BooleanOutput extends APIOutput {
    private static final long serialVersionUID = -3492135879620363481L;
    private Boolean result;
}
