package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import io.spiffy.common.api.media.dto.Content;

@Data
@EqualsAndHashCode(callSuper = false)
public class UploadResponse extends AjaxResponse {
    private final Content content;
}
