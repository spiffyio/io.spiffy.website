package io.spiffy.media.api;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.input.GetInput;
import io.spiffy.common.api.media.output.GetMediaOutput;
import io.spiffy.media.service.ContentService;

@RequestMapping("/api/media/getmedia")
public class GetMediaAPI extends API<GetInput, GetMediaOutput, ContentService> {

    @Inject
    public GetMediaAPI(final ContentService service) {
        super(GetInput.class, service);
    }

    protected GetMediaOutput api(final GetInput input) {
        if (StringUtils.isNotEmpty(input.getName())) {
            return service.getContent(input.getName());
        }

        return service.getContent(input.getId());
    }
}