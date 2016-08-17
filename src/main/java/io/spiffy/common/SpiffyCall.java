package io.spiffy.common;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import com.google.common.cache.Cache;

import io.spiffy.common.config.AppConfig;
import io.spiffy.common.dto.Context;
import io.spiffy.common.util.CsrfUtil;
import io.spiffy.common.util.JsonUtil;

public abstract class SpiffyCall<Input, Output> extends Call<Input, Output> {

    protected SpiffyCall(final Class<Output> outputClass, final WebTarget target) {
        super(outputClass, target);
    }

    protected SpiffyCall(final Class<Output> outputClass, final WebTarget target, final Cache<Input, Output> cache) {
        super(outputClass, target, cache);
    }

    @Override
    public Invocation.Builder getBuilder(final WebTarget target, final Input input) {
        final Invocation.Builder builder = super.getBuilder(target, input);
        builder.header(Context.SPIFFY_API_CERTIFICATE,
                CsrfUtil.generateToken(JsonUtil.serialize(input), AppConfig.getApiKey()));
        return builder;
    }
}
