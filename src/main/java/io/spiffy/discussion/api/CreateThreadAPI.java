package io.spiffy.discussion.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.discussion.input.CreateThreadInput;
import io.spiffy.common.api.discussion.output.CreateThreadOutput;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.discussion.service.ThreadService;

@RequestMapping("/api/discussion/createthread")
public class CreateThreadAPI extends API<CreateThreadInput, CreateThreadOutput, ThreadService> {

    @Inject
    public CreateThreadAPI(final ThreadService service, final UserClient userClient) {
        super(CreateThreadInput.class, service);
    }

    protected CreateThreadOutput api(final CreateThreadInput input) {
        return service.createThread(input.getThread(), input.getAccountId(), input.getParticipants());
    }
}