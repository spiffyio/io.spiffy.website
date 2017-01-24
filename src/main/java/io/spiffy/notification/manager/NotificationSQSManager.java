package io.spiffy.notification.manager;

import javax.inject.Inject;

import com.amazonaws.services.sqs.AmazonSQSClient;

import io.spiffy.common.dto.EntityType;
import io.spiffy.common.event.CommentEvent;
import io.spiffy.common.event.CommentNotificationEvent;
import io.spiffy.common.manager.SQSManager;
import io.spiffy.common.util.JsonUtil;
import io.spiffy.notification.service.AlertService;

public class NotificationSQSManager extends SQSManager<CommentEvent> {

    public static final String QUEUE_NAME = "spiffyio-notification";

    private final AlertService service;

    @Inject
    public NotificationSQSManager(final AmazonSQSClient client, final AlertService service) {
        super(client, CommentEvent.class, QUEUE_NAME);
        this.service = service;
    }

    public void process(final CommentEvent event, final String json) {
        if (CommentNotificationEvent.SUB_TYPE.equalsIgnoreCase(event.getSubType())) {
            final CommentNotificationEvent notification = JsonUtil.deserialize(CommentNotificationEvent.class, json);
            for (final long account : notification.getSubscriberIds()) {
                service.post(account, "comment:" + notification.getCommentId(), EntityType.POST, "" + notification.getPostId());
            }
        }
    }
}