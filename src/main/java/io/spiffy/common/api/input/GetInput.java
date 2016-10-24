package io.spiffy.common.api.input;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetInput extends APIInput {
    private Long id;
    private String name;

    public GetInput(final long id) {
        this.id = id;
    }

    public GetInput(final String name) {
        this.name = name;
    }
}