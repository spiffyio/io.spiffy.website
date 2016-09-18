package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import io.spiffy.common.api.discussion.dto.MessengerMessage;

@Data
@EqualsAndHashCode(callSuper = false)
public class MessageResponse extends AjaxResponse {
    private final MessengerMessage message;
}