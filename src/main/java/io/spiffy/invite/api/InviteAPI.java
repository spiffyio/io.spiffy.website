package io.spiffy.invite.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.api.invite.InviteInput;
import io.spiffy.api.invite.InviteOutput;
import io.spiffy.common.API;
import io.spiffy.invite.service.InviteService;

@RequestMapping("/api/invite")
public class InviteAPI extends API<InviteInput, InviteOutput, InviteService> {

    @Inject
    public InviteAPI(final InviteService service) {
        super(InviteInput.class, service);
    }

    protected InviteOutput api(final InviteInput input) {
        service.post(input.getEmail());
        return InviteOutput.builder().success(true).build();
    }
}
