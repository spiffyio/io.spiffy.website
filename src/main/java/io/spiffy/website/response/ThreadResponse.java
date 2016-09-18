package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import io.spiffy.common.api.discussion.dto.MessengerThread;

@Data
@EqualsAndHashCode(callSuper = false)
public class ThreadResponse extends AjaxResponse {
    private final MessengerThread thread;
}
