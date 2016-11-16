package io.spiffy.user.service;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.email.client.EmailClient;
import io.spiffy.common.api.email.dto.EmailProperties;
import io.spiffy.common.api.email.dto.EmailType;
import io.spiffy.common.api.security.client.SecurityClient;
import io.spiffy.common.api.user.dto.Credentials;
import io.spiffy.common.api.user.dto.Provider;
import io.spiffy.common.api.user.output.AuthenticateAccountOutput;
import io.spiffy.common.api.user.output.RecoverAccountOutput;
import io.spiffy.common.api.user.output.RegisterAccountOutput;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.util.UIDUtil;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.user.entity.AccountEntity;
import io.spiffy.user.entity.CredentialEntity;
import io.spiffy.user.entity.SessionEntity;
import io.spiffy.user.entity.TemporaryCredentialEntity;
import io.spiffy.user.repository.AccountRepository;

public class AccountService extends Service<AccountEntity, AccountRepository> {

    private final CredentialService credentialService;
    private final SessionService sessionService;
    private final TemporaryCredentialService temporaryCredentialService;
    private final EmailClient emailClient;
    private final SecurityClient securityClient;

    @Inject
    public AccountService(final AccountRepository repository, final CredentialService credentialService,
            final TemporaryCredentialService temporaryCredentialService, final SessionService sessionService,
            final EmailClient emailClient, final SecurityClient securityClient) {
        super(repository);
        this.credentialService = credentialService;
        this.sessionService = sessionService;
        this.temporaryCredentialService = temporaryCredentialService;
        this.emailClient = emailClient;
        this.securityClient = securityClient;
    }

    @Transactional
    public AccountEntity get(final Long id) {
        if (id == null) {
            return null;
        }

        final AccountEntity entity = repository.get(id);
        if (entity == null) {
            return entity;
        }

        return entity;
    }

    @Transactional
    public AccountEntity getByUserName(final String userName) {
        return repository.getByUserName(userName);
    }

    @Transactional
    public AccountEntity getByEmailAddress(final String emailAddress) {
        final long emailAddressId = getEmailAddressId(emailAddress);
        final Credentials credentials = new Credentials(Provider.EMAIL, "" + emailAddressId, null);

        final CredentialEntity entity = credentialService.getByCredentials(credentials);
        if (entity == null) {
            return null;
        }

        return entity.getAccount();
    }

    @Transactional
    public AccountEntity getByEmailAddressId(final long emailAddressId) {
        return repository.getByEmailAddressId(emailAddressId);
    }

    @Transactional
    public AccountEntity getByEmailVerificationToken(final String token) {
        final long id = securityClient.encryptString(token);
        return getByEmailVerificationTokenId(id);
    }

    @Transactional
    public AccountEntity getByEmailVerificationTokenId(final long id) {
        return repository.getByEmailVerificationTokenId(id);
    }

    @Transactional
    public AccountEntity post(final String username, final String emailAddress, final Long iconId, final Long bannerId,
            Credentials credentials) {
        validateUsername(username);

        final AccountEntity entityByUserName = getByUserName(username);

        if (credentials == null) {
            if (entityByUserName == null) {
                throw new RuntimeException();
            }
            final CredentialEntity credentialEntity = credentialService.getByAccount(entityByUserName);
            credentials = new Credentials(credentialEntity.getProvider(), credentialEntity.getProviderId(), null);
        }

        final CredentialEntity credentialEntity = credentialService.getByCredentials(credentials);
        final AccountEntity entityByCredentials = credentialEntity != null ? credentialEntity.getAccount() : null;

        ValidationUtil.validateSameOrNull("AccountEntity userName, credentials", entityByUserName, entityByCredentials);

        AccountEntity entity = entityByUserName != null ? entityByUserName : entityByCredentials;
        if (entity == null) {
            entity = new AccountEntity();
        } else {
            // TODO: archiving
        }

        entity.setUserName(username);

        final Long emailAddressId = getEmailAddressId(emailAddress);
        if (emailAddressId != null && emailAddressId != entity.getEmailAddressId()) {
            if (Provider.EMAIL.equals(credentials.getProvider())) {
                final String token = UIDUtil.generateIdempotentId();
                entity.setEmailVerificationToken(token);
                entity.setEmailVerificationTokenId(securityClient.encryptString(token));
                entity.setEmailVerified(false);
            } else {
                entity.setEmailVerified(true);
            }

            entity.setEmailAddressId(emailAddressId);
            entity.setEmailAddress(emailAddress);
        }

        if (entity.getEmailVerified() == null) {
            entity.setEmailVerified(false);
        }

        if (iconId != null) {
            entity.setIconId(iconId);
        }

        if (bannerId != null) {
            entity.setBannerId(bannerId);
        }

        repository.saveOrUpdate(entity);

        return entity;
    }

    @Transactional
    public RegisterAccountOutput register(final String username, final String email, final Credentials credentials) {
        if (Provider.EMAIL.equals(credentials.getProvider())) {
            credentials.setProviderId("" + getEmailAddressId(credentials.getProviderId()));
        }

        final CredentialEntity credentialEntity = credentialService.getByCredentials(credentials);
        if (credentialEntity != null) {
            if (credentialService.matches(credentialEntity, credentials)) {
                return new RegisterAccountOutput();
            }

            // TODO: this could be other password mismatches in the future
            return new RegisterAccountOutput(RegisterAccountOutput.Error.EXISTING_EMAIL);
        }

        AccountEntity account = getByUserName(username);
        if (account != null) {
            return new RegisterAccountOutput(RegisterAccountOutput.Error.EXISTING_USERNAME);
        }

        account = post(username, email, null, null, credentials);
        credentialService.post(account, credentials);

        if (StringUtils.isEmpty(account.getEmailVerificationToken())) {
            return new RegisterAccountOutput();
        }

        sendVerificationEmail(account, "register");

        return new RegisterAccountOutput();
    }

    private void sendRecoverEmail(final AccountEntity account, final String token, final long idempotentId) {
        final EmailProperties properties = new EmailProperties();
        properties.setName(account.getUserName());
        properties.setUrl(AppConfig.getEndpoint() + "/recover?email=" + account.getEmailAddress() + "&token=" + token);

        emailClient.sendEmailCall(EmailType.RECOVER, account.getEmailAddress(), account.getId() + ":recover:" + idempotentId,
                account.getId(), properties);
    }

    private void sendVerificationEmail(final AccountEntity account, final String idempotentId) {
        final EmailProperties properties = new EmailProperties();
        properties.setName(account.getUserName());
        properties.setUrl(AppConfig.getEndpoint() + "/verify?email=" + account.getEmailAddress() + "&token="
                + account.getEmailVerificationToken());

        emailClient.sendEmailCall(EmailType.VERIFICATION, account.getEmailAddress(),
                account.getId() + ":verification:" + idempotentId, account.getId(), properties);
    }

    @Transactional
    public AuthenticateAccountOutput authenticate(final Credentials credentials, final String sessionId,
            final String fingerprint, final String userAgent, final String ipAddress) {
        final CredentialEntity credentialEntity = credentialService.getByCredentials(credentials);
        if (credentialEntity == null) {
            if (Provider.EMAIL.equals(credentials.getProvider())) {
                return new AuthenticateAccountOutput(AuthenticateAccountOutput.Error.UNKNOWN_EMAIL);
            }
            return new AuthenticateAccountOutput(AuthenticateAccountOutput.Error.UNKNOWN_CREDENTIALS);
        }

        if (!credentialService.matches(credentialEntity, credentials)) {
            return new AuthenticateAccountOutput(AuthenticateAccountOutput.Error.INVALID_PASSWORD);
        }

        final AccountEntity account = credentialEntity.getAccount();

        final SessionEntity session = sessionService.validatedCreate(sessionId, account.getId(), fingerprint, userAgent,
                ipAddress);

        final AuthenticateAccountOutput output = new AuthenticateAccountOutput();
        output.setAccountId(account.getId());
        output.setSessionToken(session.getToken());

        return output;
    }

    @Transactional
    public AccountEntity authenticate(final String sessionId, final String token, final String userAgent,
            final String ipAddress) {
        final SessionEntity session = sessionService.authenticate(sessionId, token, userAgent, ipAddress);
        if (session == null) {
            return null;
        }

        return get(session.getAccountId());
    }

    @Transactional
    public RecoverAccountOutput recover(final String email, final String token, final String password, final String sessionId,
            final String fingerprint, final String userAgent, final String ipAddress) {
        final AccountEntity account = getByEmailAddress(email);
        if (account == null) {
            return new RecoverAccountOutput(RecoverAccountOutput.Error.UNKNOWN_EMAIL);
        }

        if (!temporaryCredentialService.matches(account.getId(), token)) {
            return new RecoverAccountOutput(RecoverAccountOutput.Error.INVALID_TOKEN);
        }

        final Long emailAddressId = getEmailAddressId(email);
        credentialService.post(account, new Credentials(Provider.EMAIL, "" + emailAddressId, password));
        temporaryCredentialService.invalidate(account.getId(), token);

        final SessionEntity session = sessionService.validatedCreate(sessionId, account.getId(), fingerprint, userAgent,
                ipAddress);

        final RecoverAccountOutput output = new RecoverAccountOutput();
        output.setAccountId(account.getId());
        output.setSessionToken(session.getToken());

        return output;
    }

    @Transactional
    public AccountEntity verify(final String token) {
        final AccountEntity account = getByEmailVerificationToken(token);
        if (account == null) {
            return null;
        }

        account.setEmailVerificationTokenId(null);
        account.setEmailVerified(true);

        repository.saveOrUpdate(account);

        return account;
    }

    @Transactional
    public AccountEntity sendRecoveryEmail(final String emailAddress) {
        final AccountEntity account = getByEmailAddress(emailAddress);
        if (account == null) {
            return null;
        }

        account.setEmailAddress(emailAddress);

        final String token = UIDUtil.generateIdempotentId();
        final TemporaryCredentialEntity entity = temporaryCredentialService.post(account.getId(), token);

        sendRecoverEmail(account, token, entity.getId());

        return account;
    }

    @Transactional
    public AccountEntity sendVerificationEmail(final long accountId, final String email, final String idempotentId) {
        final AccountEntity account = get(accountId);
        if (account == null) {
            return null;
        }

        final long emailAddressId = getEmailAddressId(email);

        final String token = UIDUtil.generateIdempotentId();
        account.setEmailVerificationToken(token);
        account.setEmailVerificationTokenId(securityClient.encryptString(token));
        account.setEmailVerified(false);
        account.setEmailAddressId(emailAddressId);
        account.setEmailAddress(email);

        repository.saveOrUpdate(account);

        sendVerificationEmail(account, idempotentId);

        return account;
    }

    protected void validateUsername(final String username) {
        ValidationUtil.validateUsername("UserAccountEntity.username", username);
        ValidationUtil.validateLength("UserAccountEntity.username", username, AccountEntity.MIN_USER_NAME_LENGTH,
                AccountEntity.MAX_USER_NAME_LENGTH);
    }

    private Long getEmailAddressId(final String email) {
        if (email == null) {
            return null;
        }

        return emailClient.postEmailAddress(email);
    }
}
