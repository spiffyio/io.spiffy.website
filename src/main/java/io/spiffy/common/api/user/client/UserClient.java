package io.spiffy.common.api.user.client;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import io.spiffy.common.Client;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.user.call.*;
import io.spiffy.common.api.user.dto.Session;
import io.spiffy.common.api.user.input.*;
import io.spiffy.common.api.user.output.*;
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
    final RecoverAccountCall recoverAccountCall;
    final RegisterAccountCall registerAccountCall;
    final SendRecoveryEmailCall sendRecoveryEmailCall;
    final SendVerifyEmailCall sendVerifyEmailCall;
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

    public Account getAccount(final long accountId) {
        return getAccount(new Account(accountId));
    }

    public Account getAccount(final String username) {
        return getAccount(new Account(username));
    }

    public Account getAccount(final Account account) {
        return getAccount(new GetAccountInput(account));
    }

    public Account getAccount(final GetAccountInput input) {
        final GetAccountOutput output = getAccountCall.call(input);
        return output.getAccount();
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

    public long postAccount(final String userName, final String emailAddress, final Long iconId) {
        final PostAccountInput input = new PostAccountInput(userName, emailAddress, iconId);
        final PostOutput output = postAccountCall.call(input);
        return output.getId();
    }

    public RecoverAccountOutput recoverAccount(final String email, final String token, final String password,
            final Context context, final String fingerprint) {
        final RecoverAccountInput input = new RecoverAccountInput(email, token, password, context.getSessionId(), fingerprint,
                context.getUserAgent(), context.getIPAddress());
        return recoverAccountCall.call(input);
    }

    public RegisterAccountOutput registerAccount(final String userName, final String emailAddress, final String password,
            final Context context, final String fingerprint) {
        final RegisterAccountInput input = new RegisterAccountInput(userName, emailAddress, password);
        final RegisterAccountOutput output = registerAccountCall.call(input);

        if (output.getError() == null) {
            final String token = authenticateAccount(emailAddress, password, context, fingerprint).getSessionToken();
            output.setSessionToken(token);
        }

        return output;
    }

    public boolean sendRecoveryEmail(final String email) {
        final SendRecoveryEmailInput input = new SendRecoveryEmailInput(email);
        final PostOutput output = sendRecoveryEmailCall.call(input);
        return output.getId() != null;
    }

    public boolean sendVerifyEmail(final Context context, final String email, final String idempotentId) {
        final SendVerifyEmailInput input = new SendVerifyEmailInput(context.getAccountId(), email, idempotentId);
        final PostOutput output = sendVerifyEmailCall.call(input);
        return output.getId() != null;
    }

    public boolean verifyEmail(final String token) {
        final VerifyEmailInput input = new VerifyEmailInput(token);
        final PostOutput output = verifyEmailCall.call(input);
        return output.getId() != null;
    }
}
