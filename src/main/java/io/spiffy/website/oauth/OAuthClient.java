package io.spiffy.website.oauth;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.config.EnvironmentVariableConfig.Initialized;
import io.spiffy.common.dto.Context;

public class OAuthClient extends Client {

    public enum Provider {
        FACEBOOK, GOOGLE
    }

    private final Map<Provider, AuthenticationCall> authenticationCalls;
    private final Map<Provider, InformationCall> informationCalls;
    private final Map<Provider, String> clientIds;
    private final Map<Provider, String> clientSecrets;

    @Inject
    public OAuthClient(final Initialized initialized, final FacebookAuthenticationCall facebookAuthenticationCall,
            final GoogleAuthenticationCall googleAuthenticationCall, final FacebookInformationCall facebookInformationCall,
            final GoogleInformationCall googleInformationCall) {
        final Map<Provider, AuthenticationCall> authenticationCalls = new HashMap<>();
        authenticationCalls.put(Provider.FACEBOOK, facebookAuthenticationCall);
        authenticationCalls.put(Provider.GOOGLE, googleAuthenticationCall);
        this.authenticationCalls = Collections.unmodifiableMap(authenticationCalls);

        final Map<Provider, InformationCall> informationCalls = new HashMap<>();
        informationCalls.put(Provider.FACEBOOK, facebookInformationCall);
        informationCalls.put(Provider.GOOGLE, googleInformationCall);
        this.informationCalls = Collections.unmodifiableMap(informationCalls);

        final Map<Provider, String> clientIds = new HashMap<>();
        clientIds.put(Provider.FACEBOOK, AppConfig.getFacebookClientId());
        clientIds.put(Provider.GOOGLE, AppConfig.getGoogleClientId());
        this.clientIds = Collections.unmodifiableMap(clientIds);

        final Map<Provider, String> clientSecrets = new HashMap<>();
        clientSecrets.put(Provider.FACEBOOK, AppConfig.getFacebookClientSecret());
        clientSecrets.put(Provider.GOOGLE, AppConfig.getGoogleClientSecret());
        this.clientSecrets = Collections.unmodifiableMap(clientSecrets);
    }

    public InformationOutput authenticate(final Context context, final String state, final String code,
            final Provider provider) {
        final String uri = AppConfig.getEndpoint() + "/login?provider=" + provider.name().toLowerCase();

        final String clientId = clientIds.get(provider);
        final String clientSecret = clientSecrets.get(provider);

        final AuthenticationInput input = new AuthenticationInput(clientId, clientSecret, uri, code);
        final AuthenticationOutput output = authenticationCalls.get(provider).call(input);
        return informationCalls.get(provider).call(new InformationInput(output.getAccessToken()));
    }
}
