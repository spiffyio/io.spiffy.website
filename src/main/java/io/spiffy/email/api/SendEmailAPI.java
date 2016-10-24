package io.spiffy.email.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.email.input.SendEmailInput;
import io.spiffy.common.api.output.PostOutput;
import io.spiffy.email.entity.EmailEntity;
import io.spiffy.email.service.EmailService;

@RequestMapping("/api/email/sendemail")
public class SendEmailAPI extends API<SendEmailInput, PostOutput, EmailService> {

    @Inject
    public SendEmailAPI(final EmailService service) {
        super(SendEmailInput.class, service);
    }

    protected PostOutput api(final SendEmailInput input) {
        final EmailEntity entity = service.send(input.getIdempotentId(), input.getAddress(), input.getType(),
                input.getAccountId(), input.getProperties());
        final Long id = entity != null ? entity.getId() : null;
        return new PostOutput(id);
    }
}