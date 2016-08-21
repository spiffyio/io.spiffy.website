package io.spiffy.user.service;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.email.client.EmailClient;
import io.spiffy.common.api.email.dto.EmailProperties;
import io.spiffy.common.api.email.dto.EmailType;
import io.spiffy.common.api.security.client.SecurityClient;
import io.spiffy.common.api.user.output.AuthenticateAccountOutput;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.util.UIDUtil;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.user.entity.AccountEntity;
import io.spiffy.user.entity.SessionEntity;
import io.spiffy.user.repository.AccountRepository;

public class AccountService extends Service<AccountEntity, AccountRepository> {

    private final CredentialService credentialService;
    private final SessionService sessionService;
    private final EmailClient emailClient;
    private final SecurityClient securityClient;

    @Inject
    public AccountService(final AccountRepository repository, final CredentialService credentialService,
            final SessionService sessionService, final EmailClient emailClient, final SecurityClient securityClient) {
        super(repository);
        this.credentialService = credentialService;
        this.sessionService = sessionService;
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
        return getByEmailAddressId(emailAddressId);
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
    public AccountEntity post(final String userName, final String emailAddress) {
        validateUserName(userName);

        final AccountEntity entityByUserName = getByUserName(userName);

        final long emailAddressId = getEmailAddressId(emailAddress);
        final AccountEntity entityByEmailAddressId = getByEmailAddressId(emailAddressId);

        ValidationUtil.validateSameOrNull("AccountEntity userName, emailAddressId", entityByUserName, entityByEmailAddressId);

        AccountEntity entity = entityByUserName != null ? entityByUserName : entityByEmailAddressId;
        if (entity == null) {
            entity = new AccountEntity();
        } else {
            // TODO: archiving
        }

        entity.setUserName(userName);

        if (entity.getEmailAddressId() == null || emailAddressId != entity.getEmailAddressId()) {
            final String token = UIDUtil.generateIdempotentId();
            entity.setEmailVerificationToken(token);
            entity.setEmailVerificationTokenId(securityClient.encryptString(token));
            entity.setEmailVerified(false);
        }

        entity.setEmailAddressId(emailAddressId);
        entity.setEmailAddress(emailAddress);

        repository.saveOrUpdate(entity);

        return entity;
    }

    @Transactional
    public AccountEntity register(final String userName, final String emailAddress, final String password) {
        // FIXME: make sure people don't just take over an account, and change password

        final AccountEntity account = post(userName, emailAddress);
        credentialService.post(account.getId(), password);

        if (StringUtils.isEmpty(account.getEmailVerificationToken())) {
            return account;
        }

        sendVerificationEmail(account);

        return account;
    }

    private void sendVerificationEmail(final AccountEntity account) {
        final EmailProperties properties = new EmailProperties();
        properties.setName(account.getUserName());
        properties.setUrl(AppConfig.getEndpoint() + "/verify?email=" + account.getEmailVerificationToken());

        emailClient.sendEmailCall(EmailType.Verify, account.getEmailAddress(),
                account.getId() + ":verification:" + account.getEmailVerificationTokenId(), account.getId(), properties);
    }

    @Transactional
    public AuthenticateAccountOutput authenticate(final String email, final String password, final String sessionId,
            final String fingerprint, final String userAgent, final String ipAddress) {
        final AccountEntity account = getByEmailAddress(email);
        if (account == null) {
            return null;
        }

        if (!credentialService.matches(account.getId(), password)) {
            return null;
        }

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
    public AccountEntity sendVerificationEmail(final long accountId, final String email) {
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

        sendVerificationEmail(account);

        return account;
    }

    protected void validateUserName(final String userName) {
        ValidationUtil.validateLength("UserAccountEntity.userName", userName, AccountEntity.MIN_USER_NAME_LENGTH,
                AccountEntity.MAX_USER_NAME_LENGTH);
    }

    private long getEmailAddressId(final String emailAddress) {
        return emailClient.postEmailAddress(emailAddress);
    }
}
