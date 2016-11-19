package io.spiffy.website.oauth;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.user.dto.Provider;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.config.EnvironmentVariableConfig.Initialized;
import io.spiffy.common.dto.Context;

public class OAuthClient extends Client {

    private final Map<Provider, AuthenticationCall> authenticationCalls;
    private final Map<Provider, InformationCall> informationCalls;
    private final Map<Provider, String> clientIds;
    private final Map<Provider, String> clientSecrets;
    private final Map<Provider, String> urlTemplates;

    @Inject
    public OAuthClient(final Initialized initialized, final AmazonAuthenticationCall amazonAuthenticationCall,
            final FacebookAuthenticationCall facebookAuthenticationCall,
            final GoogleAuthenticationCall googleAuthenticationCall, final AmazonInformationCall amazonInformationCall,
            final FacebookInformationCall facebookInformationCall, final GoogleInformationCall googleInformationCall) {
        final Map<Provider, AuthenticationCall> authenticationCalls = new HashMap<>();
        authenticationCalls.put(Provider.AMAZON, amazonAuthenticationCall);
        authenticationCalls.put(Provider.FACEBOOK, facebookAuthenticationCall);
        authenticationCalls.put(Provider.GOOGLE, googleAuthenticationCall);
        this.authenticationCalls = Collections.unmodifiableMap(authenticationCalls);

        final Map<Provider, InformationCall> informationCalls = new HashMap<>();
        informationCalls.put(Provider.AMAZON, amazonInformationCall);
        informationCalls.put(Provider.FACEBOOK, facebookInformationCall);
        informationCalls.put(Provider.GOOGLE, googleInformationCall);
        this.informationCalls = Collections.unmodifiableMap(informationCalls);

        final Map<Provider, String> clientIds = new HashMap<>();
        clientIds.put(Provider.AMAZON, AppConfig.getAmazonClientId());
        clientIds.put(Provider.FACEBOOK, AppConfig.getFacebookClientId());
        clientIds.put(Provider.GOOGLE, AppConfig.getGoogleClientId());
        this.clientIds = Collections.unmodifiableMap(clientIds);

        final Map<Provider, String> clientSecrets = new HashMap<>();
        clientSecrets.put(Provider.AMAZON, AppConfig.getAmazonClientSecret());
        clientSecrets.put(Provider.FACEBOOK, AppConfig.getFacebookClientSecret());
        clientSecrets.put(Provider.GOOGLE, AppConfig.getGoogleClientSecret());
        this.clientSecrets = Collections.unmodifiableMap(clientSecrets);

        final Map<Provider, String> urlTemplates = new HashMap<>();
        urlTemplates.put(Provider.AMAZON,
                "https://www.amazon.com/ap/oa?client_id=%s&response_type=code&scope=profile&state=%s&redirect_uri=%s");
        urlTemplates.put(Provider.FACEBOOK,
                "https://www.facebook.com/v2.8/dialog/oauth?client_id=%s&response_type=code&scope=email&state=%s&redirect_uri=%s");
        urlTemplates.put(Provider.GOOGLE,
                "https://accounts.google.com/o/oauth2/v2/auth?client_id=%s&response_type=code&scope=email&state=%s&redirect_uri=%s");
        this.urlTemplates = Collections.unmodifiableMap(urlTemplates);
    }

    public InformationOutput authenticate(final Context context, final String state, final String code, final String provider) {
        return authenticate(context, state, code, getProvider(provider));
    }

    public InformationOutput authenticate(final Context context, final String state, final String code,
            final Provider provider) {
        final String uri = AppConfig.getEndpoint() + "/login?provider=" + provider.name().toLowerCase();

        final String clientId = clientIds.get(provider);
        final String clientSecret = clientSecrets.get(provider);

        final AuthenticationInput input = new AuthenticationInput(clientId, clientSecret, uri, code);
        final AuthenticationOutput output = authenticationCalls.get(provider).call(input);

        System.out.println(output);

        return informationCalls.get(provider).call(new InformationInput(output.getAccessToken()));
    }

    public String getUrl(final Context context, final String provider) {
        return getUrl(context, getProvider(provider));
    }

    public String getUrl(final Context context, final Provider provider) {
        final String urlTemplate = urlTemplates.get(provider);
        final String clientId = clientIds.get(provider);
        final String state = context.generateCsrfToken("login" + provider);
        final String uri = AppConfig.getEndpoint() + "/login?provider=" + provider.name().toLowerCase();

        return String.format(urlTemplate, clientId, state, uri);
    }

    public Provider getProvider(final String provider) {
        return Provider.valueOf(provider.toUpperCase());
    }
}
