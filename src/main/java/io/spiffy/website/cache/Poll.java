package io.spiffy.website.cache;

import lombok.Data;

import java.io.Serializable;

@Data
public class Poll implements Serializable {
    private static final long serialVersionUID = 7397770425072264118L;

    private long notifications;
}