package io.spiffy.website.google;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import io.spiffy.common.Client;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.dto.Context;
import io.spiffy.common.exception.MissingParameterException;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GoogleClient extends Client {

    private final RecaptchaCall recaptchaCall;

    public boolean recaptcha(final Context context, final String recaptcha) {
        if (!AppConfig.isRequireRecaptcha()) {
            return true;
        }

        if (StringUtils.isEmpty(recaptcha)) {
            throw new MissingParameterException("recaptcha");
        }

        final RecaptchaInput input = new RecaptchaInput(recaptcha, context.getIPAddress());
        final RecaptchaOutput output = recaptchaCall.call(input);
        return output.getSuccess();
    }
}
