package io.spiffy.user.service;

import java.util.UUID;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.email.client.EmailClient;
import io.spiffy.common.api.email.dto.EmailProperties;
import io.spiffy.common.api.email.dto.EmailType;
import io.spiffy.common.api.user.output.AuthenticateAccountOutput;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.user.entity.AccountEntity;
import io.spiffy.user.entity.SessionEntity;
import io.spiffy.user.repository.AccountRepository;

public class AccountService extends Service<AccountEntity, AccountRepository> {

    private final CredentialService credentialService;
    private final SessionService sessionService;
    private final EmailClient emailClient;

    @Inject
    public AccountService(final AccountRepository repository, final CredentialService credentialService,
            final SessionService sessionService, final EmailClient emailClient) {
        super(repository);
        this.credentialService = credentialService;
        this.sessionService = sessionService;
        this.emailClient = emailClient;
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
        entity.setEmailAddressId(emailAddressId);

        repository.saveOrUpdate(entity);

        return entity;
    }

    @Transactional
    public AccountEntity register(final String userName, final String emailAddress, final String password) {
        // FIXME: make sure people don't just take over an account, and change password

        final AccountEntity account = post(userName, emailAddress);
        credentialService.post(account.getId(), password);

        final EmailProperties properties = new EmailProperties();
        properties.setName(userName);
        properties.setUrl(AppConfig.getEndpoint() + "/verify?email=" + UUID.randomUUID().toString());

        emailClient.sendEmailCall(EmailType.Verify, emailAddress, account.getId() + "registration", account.getId(),
                properties);

        return account;
    }

    @Transactional
    public AuthenticateAccountOutput authenticate(final String email, final String password, final String sessionId,
            final String userAgent, final String ipAddress) {
        final AccountEntity account = getByEmailAddress(email);
        if (account == null) {
            return null;
        }

        if (!credentialService.matches(account.getId(), password)) {
            return null;
        }

        final SessionEntity session = sessionService.create(sessionId, account.getId(), userAgent, ipAddress);

        final AuthenticateAccountOutput output = new AuthenticateAccountOutput();
        output.setAccountId(account.getId());
        output.setSessionToken(session.getToken());

        return output;
    }

    protected void validateUserName(final String userName) {
        ValidationUtil.validateLength("UserAccountEntity.userName", userName, AccountEntity.MIN_USER_NAME_LENGTH,
                AccountEntity.MAX_USER_NAME_LENGTH);
    }

    private long getEmailAddressId(final String emailAddress) {
        return emailClient.postEmailAddress(emailAddress);
    }
}
