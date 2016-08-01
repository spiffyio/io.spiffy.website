package io.spiffy.security.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.security.input.PostStringInput;
import io.spiffy.security.entity.HashedStringEntity;
import io.spiffy.security.service.HashedStringService;

@RequestMapping("/api/security/hashstring")
public class HashStringAPI extends API<PostStringInput, PostOutput, HashedStringService> {

    @Inject
    public HashStringAPI(final HashedStringService service) {
        super(PostStringInput.class, service);
    }

    protected PostOutput api(final PostStringInput input) {
        final HashedStringEntity entity = service.post(input.getPlainString());
        return new PostOutput(entity.getId());
    }
}
