package io.spiffy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Social {
    private String user;
    private String description;
    private String image;
    private String url;
    private String title;
}
