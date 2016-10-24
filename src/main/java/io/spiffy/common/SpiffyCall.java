package io.spiffy.common;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.api.input.APIInput;
import io.spiffy.common.api.output.APIOutput;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.dto.Context;
import io.spiffy.common.manager.APICacheManager;
import io.spiffy.common.util.CsrfUtil;
import io.spiffy.common.util.JsonUtil;

public abstract class SpiffyCall<Input extends APIInput, Output extends APIOutput> extends Call<Input, Output> {

    private final APICacheManager<Input, Output> cache;

    protected SpiffyCall(final Class<Output> outputClass, final WebTarget target) {
        this(outputClass, target, null);
    }

    protected SpiffyCall(final Class<Output> outputClass, final WebTarget target, final APICacheManager<Input, Output> cache) {
        super(outputClass, target);
        this.cache = cache;
    }

    public Output call(final Input input) {
        if (cache == null) {
            return super.call(input);
        }

        return cache.get(input, () -> super.call(input));
    }

    @Override
    public Invocation.Builder getBuilder(final WebTarget target, final Input input) {
        final Invocation.Builder builder = super.getBuilder(target, input);
        builder.header(Context.SPIFFY_API_CERTIFICATE,
                CsrfUtil.generateToken(JsonUtil.serialize(input), AppConfig.getApiKey()));
        return builder;
    }
}
