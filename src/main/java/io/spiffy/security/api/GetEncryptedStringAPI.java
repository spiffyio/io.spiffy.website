package io.spiffy.security.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.input.GetInput;
import io.spiffy.common.api.security.output.GetStringOutput;
import io.spiffy.security.entity.EncryptedStringEntity;
import io.spiffy.security.service.EncryptedStringService;

@RequestMapping("/api/security/getencryptedstring")
public class GetEncryptedStringAPI extends API<GetInput, GetStringOutput, EncryptedStringService> {

    @Inject
    public GetEncryptedStringAPI(final EncryptedStringService service) {
        super(GetInput.class, service);
    }

    protected GetStringOutput api(final GetInput input) {
        final EncryptedStringEntity entity = service.get(input.getId());
        return new GetStringOutput(entity.getPlainString());
    }
}
