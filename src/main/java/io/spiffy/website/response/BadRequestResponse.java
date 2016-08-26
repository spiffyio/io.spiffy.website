package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BadRequestResponse extends AjaxResponse {
    private final String error;
    private final String reason;
    private final String tip;

    public BadRequestResponse(final String error, final String reason) {
        this(error, reason, null);
    }
}
