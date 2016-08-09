package io.spiffy.website.google;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.dto.Context;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class GoogleClient extends Client {

    private final RecaptchaCall recaptchaCall;

    public boolean recaptcha(final Context context, final String recaptcha) {
        final RecaptchaInput input = new RecaptchaInput(recaptcha, context.getIPAddress());
        final RecaptchaOutput output = recaptchaCall.call(input);
        return output.getSuccess();
    }
}
