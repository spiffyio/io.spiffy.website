package io.spiffy.common.dto;

import java.io.Serializable;

public interface Cacheable extends Serializable {

    public boolean isCacheable();

}
