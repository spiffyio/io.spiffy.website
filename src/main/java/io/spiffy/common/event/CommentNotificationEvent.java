package io.spiffy.common.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentNotificationEvent extends CommentEvent {
    public static final String SUB_TYPE = "POSTED";

    private Long postId;
    private Set<Long> subscriberIds;

    public CommentNotificationEvent() {
        super.setSubType(SUB_TYPE);
    }
}
