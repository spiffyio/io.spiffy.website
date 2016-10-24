package io.spiffy.common.api.email.output;

import lombok.*;

import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetEmailAddressOutput extends APIOutput {
    private static final long serialVersionUID = -6463328208526046062L;
    private String address;
}
