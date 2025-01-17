package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import io.spiffy.common.api.discussion.dto.MessengerThread;

@Data
@EqualsAndHashCode(callSuper = false)
public class ThreadsResponse extends AjaxResponse {
    private final List<MessengerThread> threads;
}
