package io.spiffy.common;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public abstract class Call<Input, Output> extends Manager {

    private final Class<Output> outputClass;
    private final WebTarget target;

    protected Call(final Class<Output> outputClass, final WebTarget target) {
        this.outputClass = outputClass;
        this.target = target;
    }

    public Output call(final Input input) {
        final Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);
        builder.accept(MediaType.APPLICATION_JSON);

        final Response response = builder.post(Entity.json(input));
        return response.readEntity(outputClass);
    }
}
