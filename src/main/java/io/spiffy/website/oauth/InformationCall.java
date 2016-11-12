package io.spiffy.website.oauth;

import javax.inject.Inject;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import org.springframework.web.bind.annotation.RequestMethod;

import io.spiffy.common.Call;

public abstract class InformationCall extends Call<InformationInput, InformationOutput> {

    @Inject
    public InformationCall(final WebTarget target) {
        super(InformationOutput.class, target, RequestMethod.GET);
    }

    @Override
    public Invocation.Builder getBuilder(final WebTarget target, final InformationInput input) {
        final WebTarget webTarget = target.queryParam("access_token", input.getAccessToken());

        return super.getBuilder(webTarget, input);
    }
}
