package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import io.spiffy.common.api.media.dto.MediaType;

@Data
@EqualsAndHashCode(callSuper = false)
public class UploadResponse extends AjaxResponse {
    private final String id;
    private final String url;
    private final List<MediaType> types;
}
