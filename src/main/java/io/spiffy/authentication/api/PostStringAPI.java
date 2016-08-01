package io.spiffy.authentication.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.authentication.service.HashedStringService;
import io.spiffy.common.API;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.authentication.input.PostStringInput;

@RequestMapping("/api/authentication/poststring")
public class PostStringAPI extends API<PostStringInput, PostOutput, HashedStringService> {

    @Inject
    public PostStringAPI(final HashedStringService service) {
        super(PostStringInput.class, service);
    }

    protected PostOutput api(final PostStringInput input) {
        service.post(input.getPlainString());
        return new PostOutput(SUCCESS);
    }
}
