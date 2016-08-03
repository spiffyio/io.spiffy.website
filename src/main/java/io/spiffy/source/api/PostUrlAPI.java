package io.spiffy.source.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.source.input.PostUrlInput;
import io.spiffy.source.entity.UrlEntity;
import io.spiffy.source.service.UrlService;

@RequestMapping("/api/source/posturl")
public class PostUrlAPI extends API<PostUrlInput, PostOutput, UrlService> {

    @Inject
    public PostUrlAPI(final UrlService service) {
        super(PostUrlInput.class, service);
    }

    protected PostOutput api(final PostUrlInput input) {
        final UrlEntity entity = service.post(input.getUrl(), input.getDomain(), input.getEntityId(), input.getEntityOwner());
        final Long id = entity != null ? entity.getId() : null;
        return new PostOutput(id);
    }
}