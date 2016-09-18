package io.spiffy.discussion.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.discussion.input.GetMessagesInput;
import io.spiffy.common.api.discussion.output.GetMessagesOutput;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.discussion.service.ThreadService;

@RequestMapping("/api/discussion/getmessages")
public class GetMessagesAPI extends API<GetMessagesInput, GetMessagesOutput, ThreadService> {

    @Inject
    public GetMessagesAPI(final ThreadService service, final UserClient userClient) {
        super(GetMessagesInput.class, service);
    }

    protected GetMessagesOutput api(final GetMessagesInput input) {
        return service.getMessages(input.getThread(), input.getAccountId(), input.getParticipants(), input.getAfter());
    }
}