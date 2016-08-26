package io.spiffy.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MissingParameterException extends InvalidParameterException {

    private static final long serialVersionUID = 3421836275791854474L;

    public MissingParameterException(final String parameterName) {
        super(parameterName, "required");
    }
}
