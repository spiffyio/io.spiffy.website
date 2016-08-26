package io.spiffy.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InvalidParameterException extends RuntimeException {

    private static final long serialVersionUID = -6283223267424767587L;

    private final String parameterName;
    private final String reason;
}
