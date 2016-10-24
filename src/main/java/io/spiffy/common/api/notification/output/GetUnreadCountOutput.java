package io.spiffy.common.api.notification.output;

import lombok.*;

import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetUnreadCountOutput extends APIOutput {
    private static final long serialVersionUID = 378177899451008107L;
    private Long count;
}
