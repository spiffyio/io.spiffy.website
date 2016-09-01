package io.spiffy.common.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaDeletedEvent extends MediaEvent {
    public static final String SUB_TYPE = "DELETED";

    public MediaDeletedEvent() {
        super.setSubType(SUB_TYPE);
    }
}
