package io.spiffy.common.api.media.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.spiffy.common.api.media.dto.MediaType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMediaOutput {
    private String url;
    private String name;
    private List<MediaType> types;
}
