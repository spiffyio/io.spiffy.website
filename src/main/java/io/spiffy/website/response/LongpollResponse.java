package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LongpollResponse extends AjaxResponse {
    private long notifications;
}