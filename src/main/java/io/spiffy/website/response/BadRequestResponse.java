package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BadRequestResponse extends AjaxResponse {
    private final String error;
    private final String reason;
}
