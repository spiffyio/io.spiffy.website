package io.spiffy.notification.manager;

import javax.inject.Inject;

import com.amazonaws.services.sqs.AmazonSQSClient;

import io.spiffy.common.dto.EntityType;
import io.spiffy.common.event.CommentEvent;
import io.spiffy.common.event.CommentPostedEvent;
import io.spiffy.common.manager.SQSManager;
import io.spiffy.notification.service.AlertService;

public class NotificationSQSManager extends SQSManager<CommentEvent> {

    private static final String QUEUE_NAME = "spiffyio-notification";

    private final AlertService service;

    @Inject
    public NotificationSQSManager(final AmazonSQSClient client, final AlertService service) {
        super(client, CommentEvent.class, QUEUE_NAME);
        this.service = service;
    }

    public void process(final CommentEvent comment, final String json) {
        if (CommentPostedEvent.SUB_TYPE.equalsIgnoreCase(comment.getSubType())) {
            for (final long account : comment.getSubscriberIds()) {
                service.post(account, "comment:" + comment.getCommentId(), EntityType.POST, "" + comment.getPostId());
            }
        }
    }
}