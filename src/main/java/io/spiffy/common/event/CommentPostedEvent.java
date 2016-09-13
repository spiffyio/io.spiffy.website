package io.spiffy.common.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentPostedEvent extends CommentEvent {
    public static final String SUB_TYPE = "POSTED";

    public CommentPostedEvent() {
        super.setSubType(SUB_TYPE);
    }
}
