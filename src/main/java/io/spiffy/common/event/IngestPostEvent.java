package io.spiffy.common.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngestPostEvent extends IngestEvent {
    public static final String SUB_TYPE = "POST";

    private String address;
    private String description;

    public IngestPostEvent() {
        super.setSubType(SUB_TYPE);
    }
}
