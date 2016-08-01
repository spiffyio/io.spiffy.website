package io.spiffy.user.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.email.client.GetEmailAddressClient;
import io.spiffy.common.api.email.client.PostEmailAddressClient;
import io.spiffy.common.api.email.input.PostEmailAddressInput;
import io.spiffy.common.exception.ValidationException;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.user.entity.UserAccountEntity;
import io.spiffy.user.repository.UserAccountRepository;

public class UserAccountService extends Service<UserAccountEntity, UserAccountRepository> {

    private final GetEmailAddressClient getEmailAddressClient;
    private final PostEmailAddressClient postEmailAddressClient;

    @Inject
    public UserAccountService(final UserAccountRepository repository, final GetEmailAddressClient getEmailAddressClient,
            final PostEmailAddressClient postEmailAddressClient) {
        super(repository);
        this.getEmailAddressClient = getEmailAddressClient;
        this.postEmailAddressClient = postEmailAddressClient;
    }

    @Transactional
    public UserAccountEntity get(final Long id) {
        if (id == null) {
            return null;
        }

        final UserAccountEntity entity = repository.get(id);
        if (entity == null) {
            return entity;
        }

        return entity;
    }

    @Transactional
    public UserAccountEntity getByUserName(final String userName) {
        return repository.getByUserName(userName);
    }

    @Transactional
    public UserAccountEntity getByEmailAddress(final String emailAddress) {
        final long emailAddressId = getEmailAddressId(emailAddress);
        return getByEmailAddressId(emailAddressId);
    }

    @Transactional
    public UserAccountEntity getByEmailAddressId(final long emailAddressId) {
        return repository.getByEmailAddressId(emailAddressId);
    }

    @Transactional
    public UserAccountEntity post(final String userName, final String emailAddress) {
        validateUserName(userName);

        final UserAccountEntity entityByUserName = getByUserName(userName);

        final long emailAddressId = getEmailAddressId(emailAddress);
        final UserAccountEntity entityByEmailAddressId = getByEmailAddressId(emailAddressId);

        if (entityByUserName != null && entityByEmailAddressId != null
                && entityByUserName.getId() != entityByEmailAddressId.getId()) {
            throw new ValidationException(String.format(
                    "returned different accounts for the userName: %s and emailAddressId: %d", userName, emailAddressId));
        }

        UserAccountEntity entity = entityByUserName != null ? entityByUserName : entityByEmailAddressId;
        if (entity == null) {
            entity = new UserAccountEntity();
        } else {
            // TODO: archiving
        }

        entity.setUserName(userName);
        entity.setEmailAddressId(emailAddressId);

        repository.saveOrUpdate(entity);

        return entity;
    }

    protected void validateUserName(final String userName) {
        ValidationUtil.validateLength("UserAccountEntity.userName", userName, UserAccountEntity.MIN_USER_NAME_LENGTH,
                UserAccountEntity.MAX_USER_NAME_LENGTH);
    }

    private long getEmailAddressId(final String emailAddress) {
        final PostOutput output = postEmailAddressClient.call(new PostEmailAddressInput(emailAddress));
        return output.getId();
    }

}
