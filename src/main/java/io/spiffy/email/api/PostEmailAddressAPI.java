package io.spiffy.email.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.email.input.PostEmailAddressInput;
import io.spiffy.common.api.email.output.PostEmailAddressOutput;
import io.spiffy.email.entity.EmailAddressEntity;
import io.spiffy.email.service.EmailAddressService;

@RequestMapping("/api/email/postaddress")
public class PostEmailAddressAPI extends API<PostEmailAddressInput, PostEmailAddressOutput, EmailAddressService> {

    @Inject
    public PostEmailAddressAPI(final EmailAddressService service) {
        super(PostEmailAddressInput.class, service);
    }

    protected PostEmailAddressOutput api(final PostEmailAddressInput input) {
        final EmailAddressEntity entity = service.post(input.getAddress());
        final Long id = entity != null ? entity.getId() : null;
        return new PostEmailAddressOutput(id);
    }
}