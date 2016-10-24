package io.spiffy.common.api.output;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.spiffy.common.dto.Cacheable;

public abstract class APIOutput implements Cacheable {
    private static final long serialVersionUID = -4166181163851674787L;

    @JsonIgnore
    public boolean isCacheable() {
        return true;
    }
}
