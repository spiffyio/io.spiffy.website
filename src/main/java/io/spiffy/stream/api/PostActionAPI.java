package io.spiffy.stream.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.stream.input.PostActionInput;
import io.spiffy.common.api.stream.output.PostActionOutput;
import io.spiffy.stream.service.PostService;

@RequestMapping("/api/stream/postaction")
public class PostActionAPI extends API<PostActionInput, PostActionOutput, PostService> {

    @Inject
    public PostActionAPI(final PostService service) {
        super(PostActionInput.class, service);
    }

    protected PostActionOutput api(final PostActionInput input) {
        return service.action(input.getPostId(), input.getAccountId(), input.getAction());
    }
}