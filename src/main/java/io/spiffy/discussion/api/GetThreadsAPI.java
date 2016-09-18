package io.spiffy.discussion.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.discussion.input.GetThreadsInput;
import io.spiffy.common.api.discussion.output.GetThreadsOutput;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.discussion.service.ThreadService;

@RequestMapping("/api/discussion/getthreads")
public class GetThreadsAPI extends API<GetThreadsInput, GetThreadsOutput, ThreadService> {

    @Inject
    public GetThreadsAPI(final ThreadService service, final UserClient userClient) {
        super(GetThreadsInput.class, service);
    }

    protected GetThreadsOutput api(final GetThreadsInput input) {
        return service.getThreads(input.getAccountId());
    }
}