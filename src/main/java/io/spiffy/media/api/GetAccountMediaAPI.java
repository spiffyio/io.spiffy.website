package io.spiffy.media.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.media.input.GetAccountMediaInput;
import io.spiffy.common.api.media.output.GetAccountMediaOutput;
import io.spiffy.media.service.ContentService;

@RequestMapping("/api/media/getaccountmedia")
public class GetAccountMediaAPI extends API<GetAccountMediaInput, GetAccountMediaOutput, ContentService> {

    @Inject
    public GetAccountMediaAPI(final ContentService service) {
        super(GetAccountMediaInput.class, service);
    }

    protected GetAccountMediaOutput api(final GetAccountMediaInput input) {
        return service.getContent(input.getAccountId(), input.getType(), input.getFirst(), input.getMaxResults(),
                input.getIncludeFirst());
    }
}