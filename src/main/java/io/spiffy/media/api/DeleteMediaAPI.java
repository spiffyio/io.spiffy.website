package io.spiffy.media.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.media.input.DeleteMediaInput;
import io.spiffy.common.api.output.PostOutput;
import io.spiffy.media.service.ContentService;

@RequestMapping("/api/media/deletemedia")
public class DeleteMediaAPI extends API<DeleteMediaInput, PostOutput, ContentService> {

    @Inject
    public DeleteMediaAPI(final ContentService service) {
        super(DeleteMediaInput.class, service);
    }

    protected PostOutput api(final DeleteMediaInput input) {
        service.delete(input.getAccountId(), input.getNames());
        return new PostOutput();
    }
}