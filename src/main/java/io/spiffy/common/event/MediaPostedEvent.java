package io.spiffy.common.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaPostedEvent extends MediaEvent {
    public static final String SUB_TYPE = "POSTED";

    public MediaPostedEvent() {
        super.setSubType(SUB_TYPE);
    }
}
