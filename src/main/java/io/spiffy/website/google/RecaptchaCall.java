package io.spiffy.website.google;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Call;
import io.spiffy.common.config.AppConfig;

public class RecaptchaCall extends Call<RecaptchaInput, RecaptchaOutput> {

    @Inject
    public RecaptchaCall(final @Named("googleWebTarget") WebTarget target) {
        super(RecaptchaOutput.class, target.path("recaptcha/api/siteverify"));
    }

    @Override
    public Invocation.Builder getBuilder(final WebTarget target, final RecaptchaInput input) {
        final WebTarget webTarget = target.queryParam("secret", AppConfig.getRecaptchaSecretKey())
                .queryParam("response", input.getResponse()).queryParam("remoteip", input.getRemoteip());
        return super.getBuilder(webTarget, input);
    }
}
