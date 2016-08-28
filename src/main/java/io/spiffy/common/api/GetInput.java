package io.spiffy.common.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetInput {
    private Long id;
    private String name;

    public GetInput(final long id) {
        this.id = id;
    }

    public GetInput(final String name) {
        this.name = name;
    }
}