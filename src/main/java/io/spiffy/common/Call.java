package io.spiffy.common;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.spiffy.common.config.AppConfig;
import io.spiffy.common.dto.Context;
import io.spiffy.common.util.CsrfUtil;
import io.spiffy.common.util.JsonUtil;

public abstract class Call<Input, Output> extends Manager {

    private final Class<Output> outputClass;
    private final WebTarget target;

    protected Call(final Class<Output> outputClass, final WebTarget target) {
        this.outputClass = outputClass;
        this.target = target;
    }

    public Output call(final Input input) {
        final String json = JsonUtil.serialize(input);

        final Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);
        builder.accept(MediaType.APPLICATION_JSON);
        builder.header(Context.SPIFFY_API_CERTIFICATE, CsrfUtil.generateToken(json, AppConfig.getApiKey()));

        final Response response = builder.post(Entity.json(json));
        return response.readEntity(outputClass);
    }
}
