package io.spiffy.discussion.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.discussion.input.PostMessageInput;
import io.spiffy.common.api.discussion.output.PostMessageOutput;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.discussion.service.ThreadService;

@RequestMapping("/api/discussion/postmessage")
public class PostMessageAPI extends API<PostMessageInput, PostMessageOutput, ThreadService> {

    @Inject
    public PostMessageAPI(final ThreadService service, final UserClient userClient) {
        super(PostMessageInput.class, service);
    }

    protected PostMessageOutput api(final PostMessageInput input) {
        return service.postMessage(input.getThread(), input.getAccountId(), input.getParticipants(), input.getIdempotentId(),
                input.getComment());
    }
}