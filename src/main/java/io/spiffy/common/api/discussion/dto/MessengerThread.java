package io.spiffy.common.api.discussion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessengerThread {
    private String id;
    private String icon;
    private String time;
    private String preview;

    @JsonIgnore
    private Date date;
}
