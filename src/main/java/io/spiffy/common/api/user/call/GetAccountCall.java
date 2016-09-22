package io.spiffy.common.api.user.call;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import com.google.common.cache.CacheBuilder;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.user.input.GetAccountInput;
import io.spiffy.common.api.user.output.GetAccountOutput;

public class GetAccountCall extends SpiffyCall<GetAccountInput, GetAccountOutput> {

    @Inject
    public GetAccountCall(final WebTarget target) {
        super(GetAccountOutput.class, target.path("user/getaccount"),
                CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(15, TimeUnit.SECONDS).build());
    }
}