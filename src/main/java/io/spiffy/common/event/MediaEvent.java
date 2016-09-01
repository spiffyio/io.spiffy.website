package io.spiffy.common.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaEvent extends Event {
    private Long mediaId;
    private Set<Long> mediaIds;

    public MediaEvent() {
        super.setType("MEDIA");
    }
}
