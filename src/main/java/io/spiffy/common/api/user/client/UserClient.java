package io.spiffy.common.api.user.client;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import io.spiffy.common.Client;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.user.call.*;
import io.spiffy.common.api.user.dto.Session;
import io.spiffy.common.api.user.input.*;
import io.spiffy.common.api.user.output.AuthenticateAccountOutput;
import io.spiffy.common.api.user.output.AuthenticateSessionOutput;
import io.spiffy.common.api.user.output.GetAccountOutput;
import io.spiffy.common.api.user.output.GetSessionsOutput;
import io.spiffy.common.dto.Account;
import io.spiffy.common.dto.Context;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class UserClient extends Client {

    final AuthenticateAccountCall authenticateAccountCall;
    final AuthenticateSessionCall authenticateSessionCall;
    final GetAccountCall getAccountCall;
    final GetSessionsCall getSessionsCall;
    final InvalidateSessionCall invalidateSessionCall;
    final PostAccountCall postAccountCall;
    final RegisterAccountCall registerAccountCall;
    final VerifyEmailCall verifyEmailCall;

    public AuthenticateAccountOutput authenticateAccount(final String email, final String password, final Context context,
            final String fingerprint) {
        final AuthenticateAccountInput input = new AuthenticateAccountInput(email, password, context.getSessionId(),
                fingerprint, context.getUserAgent(), context.getIPAddress());
        return authenticateAccountCall.call(input);
    }

    public Account authenticateSession(final Context context) {
        final String token = context.getSessionToken();
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        final AuthenticateSessionInput input = new AuthenticateSessionInput(context.getSessionId(), token,
                context.getUserAgent(), context.getIPAddress());
        final AuthenticateSessionOutput output = authenticateSessionCall.call(input);
        return output.getAccount();
    }

    public List<Session> getSessions(final long accountId) {
        final GetSessionsInput input = new GetSessionsInput(accountId);
        final GetSessionsOutput output = getSessionsCall.call(input);
        return output.getSessions();
    }

    public String getAccount(final long accountId) {
        final GetInput input = new GetInput(accountId);
        final GetAccountOutput output = getAccountCall.call(input);
        return output.getUsername();
    }

    public boolean invalidateSession(final Context context) {
        final String token = context.getSessionToken();
        if (StringUtils.isEmpty(token)) {
            return false;
        }

        final InvalidateSessionInput input = new InvalidateSessionInput(context.getSessionId(), token, context.getUserAgent(),
                context.getIPAddress());
        final PostOutput output = invalidateSessionCall.call(input);
        return output.getId() != null;
    }

    public boolean invalidateSession(final long sessionId, final Context context) {
        final InvalidateSessionInput input = new InvalidateSessionInput(sessionId, context.getAccountId(),
                context.getUserAgent(), context.getIPAddress());
        final PostOutput output = invalidateSessionCall.call(input);
        return output.getId() != null;
    }

    public long postAccount(final String userName, final String emailAddress) {
        final PostAccountInput input = new PostAccountInput(userName, emailAddress);
        final PostOutput output = postAccountCall.call(input);
        return output.getId();
    }

    public long registerAccount(final String userName, final String emailAddress, final String password) {
        final RegisterAccountInput input = new RegisterAccountInput(userName, emailAddress, password);
        final PostOutput output = registerAccountCall.call(input);
        return output.getId();
    }

    public boolean verifyEmail(final String token) {
        final VerifyEmailInput input = new VerifyEmailInput(token);
        final PostOutput output = verifyEmailCall.call(input);
        return output.getId() != null;
    }
}
