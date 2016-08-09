package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InvalidRecaptchaResponse extends AjaxResponse {
    private final String error = "recaptcha";
}
