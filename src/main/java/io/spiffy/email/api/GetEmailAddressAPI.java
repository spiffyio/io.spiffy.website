package io.spiffy.email.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.email.output.GetEmailAddressOutput;
import io.spiffy.email.entity.EmailAddressEntity;
import io.spiffy.email.service.EmailAddressService;

@RequestMapping("/api/email/getaddress")
public class GetEmailAddressAPI extends API<GetInput, GetEmailAddressOutput, EmailAddressService> {

    @Inject
    public GetEmailAddressAPI(final EmailAddressService service) {
        super(GetInput.class, service);
    }

    protected GetEmailAddressOutput api(final GetInput input) {
        final EmailAddressEntity entity = service.get(input.getId());
        final String address = entity != null ? entity.getAddress() : null;
        return new GetEmailAddressOutput(address);
    }
}