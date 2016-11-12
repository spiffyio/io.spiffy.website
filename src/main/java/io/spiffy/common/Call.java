package io.spiffy.common;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestMethod;

import io.spiffy.common.util.JsonUtil;

public abstract class Call<Input, Output> extends Manager {

    private final Class<Output> outputClass;
    private final WebTarget target;
    private final RequestMethod method;

    protected Call(final Class<Output> outputClass, final WebTarget target) {
        this(outputClass, target, RequestMethod.POST);
    }

    protected Call(final Class<Output> outputClass, final WebTarget target, final RequestMethod method) {
        this.outputClass = outputClass;
        this.target = target;
        this.method = method;
    }

    public Output call(final Input input) {
        final Invocation.Builder builder = getBuilder(target, input);
        builder.accept(MediaType.APPLICATION_JSON);

        final String injson = JsonUtil.serialize(input);

        final Response response;
        if (RequestMethod.GET.equals(method)) {
            response = builder.get();
        } else {
            response = builder.post(Entity.json(injson));
        }

        final String outjson = response.readEntity(String.class);

        return JsonUtil.deserialize(outputClass, outjson);
    }

    public Invocation.Builder getBuilder(final WebTarget target, final Input input) {
        return target.request(MediaType.APPLICATION_JSON);
    }
}
