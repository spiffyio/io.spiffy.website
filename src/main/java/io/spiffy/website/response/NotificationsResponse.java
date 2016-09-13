package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotificationsResponse extends AjaxResponse {
    private final long count;
}
