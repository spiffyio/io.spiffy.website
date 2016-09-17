package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class MessagesResponse extends AjaxResponse {
    private final List<Message> messages;

    @Data
    public static class Message {
        private final String id;
        private final String side;
        private final String icon;
        private final String message;
    }
}