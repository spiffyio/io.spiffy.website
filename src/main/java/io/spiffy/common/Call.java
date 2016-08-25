package io.spiffy.common;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import io.spiffy.common.util.JsonUtil;

public abstract class Call<Input, Output> extends Manager {

    private final Class<Output> outputClass;
    private final WebTarget target;
    private final Cache<Input, Output> cache;

    protected Call(final Class<Output> outputClass, final WebTarget target) {
        this(outputClass, target, CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(10, TimeUnit.MINUTES).build());
    }

    protected Call(final Class<Output> outputClass, final WebTarget target, final Cache<Input, Output> cache) {
        this.outputClass = outputClass;
        this.target = target;
        this.cache = cache;
    }

    public Output call(final Input input) {
        if (false && cache != null) {
            try {
                return cache.get(input, () -> doCall(input));
            } catch (final ExecutionException e) {
            }
        }

        return doCall(input);
    }

    public Invocation.Builder getBuilder(final WebTarget target, final Input input) {
        return target.request(MediaType.APPLICATION_JSON);
    }

    private Output doCall(final Input input) {
        final Invocation.Builder builder = getBuilder(target, input);
        builder.accept(MediaType.APPLICATION_JSON);

        final String injson = JsonUtil.serialize(input);
        final Response response = builder.post(Entity.json(injson));
        final String outjson = response.readEntity(String.class);

        return JsonUtil.deserialize(outputClass, outjson);
    }
}
