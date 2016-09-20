package io.spiffy.user.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.email.client.EmailClient;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.media.output.GetMediaOutput;
import io.spiffy.common.api.user.input.AuthenticateSessionInput;
import io.spiffy.common.api.user.output.AuthenticateSessionOutput;
import io.spiffy.common.dto.Account;
import io.spiffy.user.entity.AccountEntity;
import io.spiffy.user.service.AccountService;

@RequestMapping("/api/user/authenticatesession")
public class AuthenticateSessionAPI extends API<AuthenticateSessionInput, AuthenticateSessionOutput, AccountService> {

    private static final String DEFAULT_ICON = "//cdn.spiffy.io/media/DxrwtJ-Cg.jpg";

    private final EmailClient emailClient;
    private final MediaClient mediaClient;

    @Inject
    public AuthenticateSessionAPI(final AccountService service, final EmailClient emailClient, final MediaClient mediaClient) {
        super(AuthenticateSessionInput.class, service);
        this.emailClient = emailClient;
        this.mediaClient = mediaClient;
    }

    protected AuthenticateSessionOutput api(final AuthenticateSessionInput input) {
        final AccountEntity entity = service.authenticate(input.getSessionId(), input.getToken(), input.getUserAgent(),
                input.getIpAddress());
        if (entity == null) {
            return new AuthenticateSessionOutput();
        }

        final String iconUrl;
        if (entity.getIconId() != null) {
            final GetMediaOutput output = mediaClient.getMedia(entity.getIconId());
            if (output.getContent() != null) {
                iconUrl = output.getContent().getThumbnail();
            } else {
                iconUrl = DEFAULT_ICON;
            }
        } else {
            iconUrl = DEFAULT_ICON;
        }

        final Account account = new Account(entity.getId(), entity.getUserName(),
                emailClient.getEmailAddress(entity.getEmailAddressId()), entity.getEmailVerified(), iconUrl);

        return new AuthenticateSessionOutput(account);
    }
}