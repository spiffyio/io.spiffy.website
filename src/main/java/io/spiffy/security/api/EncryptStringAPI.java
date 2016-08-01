package io.spiffy.security.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.security.input.PostStringInput;
import io.spiffy.security.entity.EncryptedStringEntity;
import io.spiffy.security.service.EncryptedStringService;

@RequestMapping("/api/security/encryptstring")
public class EncryptStringAPI extends API<PostStringInput, PostOutput, EncryptedStringService> {

    @Inject
    public EncryptStringAPI(final EncryptedStringService service) {
        super(PostStringInput.class, service);
    }

    protected PostOutput api(final PostStringInput input) {
        final EncryptedStringEntity entity = service.post(input.getPlainString());
        return new PostOutput(entity.getId());
    }
}
