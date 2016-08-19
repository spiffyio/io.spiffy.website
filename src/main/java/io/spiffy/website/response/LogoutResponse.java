package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LogoutResponse extends AjaxResponse {
    private final Boolean success;
}
