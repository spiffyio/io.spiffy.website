package io.spiffy.common.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngestSourceEvent extends IngestEvent {
    public static final String SUB_TYPE = "SOURCE";

    private String address;

    public IngestSourceEvent() {
        super.setSubType(SUB_TYPE);
    }
}
