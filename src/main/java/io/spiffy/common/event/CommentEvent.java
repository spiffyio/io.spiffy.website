package io.spiffy.common.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentEvent extends Event {
    private Long commentId;

    public CommentEvent() {
        super.setType("COMMENT");
    }
}
