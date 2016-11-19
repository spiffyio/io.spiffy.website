package io.spiffy.website.oauth;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InformationOutput {
    private String id;
    private String email;

    @JsonProperty("user_id")
    public void setUserId(final String id) {
        this.id = id;
    }
}
