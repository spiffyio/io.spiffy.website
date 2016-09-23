package io.spiffy.notification.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.media.dto.Content;
import io.spiffy.common.api.notification.dto.Notification;
import io.spiffy.common.api.notification.output.GetNotificationsOutput;
import io.spiffy.common.api.stream.client.StreamClient;
import io.spiffy.common.api.stream.dto.Post;
import io.spiffy.common.dto.EntityType;
import io.spiffy.common.util.DateUtil;
import io.spiffy.notification.entity.AlertEntity;
import io.spiffy.notification.repository.AlertRepository;

public class AlertService extends Service<AlertEntity, AlertRepository> {

    private final StreamClient streamClient;

    @Inject
    public AlertService(final AlertRepository repository, final StreamClient streamClient) {
        super(repository);
        this.streamClient = streamClient;
    }

    @Transactional
    public AlertEntity get(final long account, final String idempotentId) {
        return repository.get(account, idempotentId);
    }

    @Transactional
    public GetNotificationsOutput getByAccount(final long account) {
        final List<AlertEntity> alerts = repository.getByAccount(account);

        final List<Notification> notifications = new ArrayList<>();
        for (final AlertEntity alert : alerts) {
            final Notification notification = asNotification(alert);
            if (notification != null) {
                notifications.add(notification);
            }
        }

        return new GetNotificationsOutput(notifications);
    }

    public Notification asNotification(final AlertEntity alert) {
        final Post post = streamClient.getPost(Long.parseLong(alert.getEntityId())).getPost();
        if (post == null) {
            alert.setArchivedAt(DateUtil.now());
            repository.saveOrUpdate(alert);
            return null;
        }

        final String actionUrl = "/stream/" + post.getPostId();
        final String iconUrl;

        alert.setReadAt(DateUtil.now());
        repository.saveOrUpdate(alert);

        final Content content = post.getContent();
        if (content != null) {
            iconUrl = content.getPoster() != null ? content.getPoster().getThumbnail() : content.getThumbnail();
        } else {
            return null;
        }

        final String message = "new comment!";

        return new Notification(actionUrl, iconUrl, message);
    }

    @Transactional
    public long getUnreadCount(final long account) {
        return repository.getUnreadCount(account);
    }

    @Transactional
    public AlertEntity post(final long account, final String idempotentId, final EntityType entityType, final String entityId) {
        AlertEntity entity = get(account, idempotentId);
        if (entity == null) {
            entity = new AlertEntity(account, idempotentId, entityType, entityId, DateUtil.now());
        }

        repository.saveOrUpdate(entity);

        return entity;
    }
}
