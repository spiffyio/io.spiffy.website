package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import io.spiffy.common.api.discussion.dto.MessengerMessage;

@Data
@EqualsAndHashCode(callSuper = false)
public class MessagesResponse extends AjaxResponse {
    private final List<MessengerMessage> messages;
}