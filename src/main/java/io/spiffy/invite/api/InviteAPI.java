package io.spiffy.invite.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.invite.input.InviteInput;
import io.spiffy.invite.service.InviteService;

@RequestMapping("/api/invite")
public class InviteAPI extends API<InviteInput, PostOutput, InviteService> {

    @Inject
    public InviteAPI(final InviteService service) {
        super(InviteInput.class, service);
    }

    protected PostOutput api(final InviteInput input) {
        service.post(input.getEmail());
        return new PostOutput(SUCCESS);
    }
}
