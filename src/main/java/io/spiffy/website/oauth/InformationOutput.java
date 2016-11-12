package io.spiffy.website.oauth;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InformationOutput {
    private String id;
    private String email;
}
