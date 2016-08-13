package io.spiffy.media.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.media.input.GetMediaOutput;
import io.spiffy.common.config.AppConfig;
import io.spiffy.media.entity.MediaEntity;
import io.spiffy.media.service.MediaService;

@RequestMapping("/api/media/gettmedia")
public class GetMediaAPI extends API<GetInput, GetMediaOutput, MediaService> {

    @Inject
    public GetMediaAPI(final MediaService service) {
        super(GetInput.class, service);
    }

    protected GetMediaOutput api(final GetInput input) {
        final MediaEntity entity = service.get(input.getId());

        final GetMediaOutput output = new GetMediaOutput();
        if (entity == null) {
            return output;
        }

        output.setUrl(AppConfig.getCdnEndpoint() + "/" + MediaService.getKey(entity));

        return output;
    }
}