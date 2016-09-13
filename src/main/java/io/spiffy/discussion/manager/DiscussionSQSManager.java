package io.spiffy.discussion.manager;

import javax.inject.Inject;

import com.amazonaws.services.sqs.AmazonSQSClient;

import io.spiffy.common.event.CommentEvent;
import io.spiffy.common.event.CommentPostedEvent;
import io.spiffy.common.manager.SQSManager;
import io.spiffy.discussion.service.ThreadService;

public class DiscussionSQSManager extends SQSManager<CommentEvent> {

    private static final String QUEUE_NAME = "spiffyio-discussion";

    private final ThreadService service;

    @Inject
    public DiscussionSQSManager(final AmazonSQSClient client, final ThreadService service) {
        super(client, CommentEvent.class, QUEUE_NAME);
        this.service = service;
    }

    public void process(final CommentEvent comment, final String json) {
        if (CommentPostedEvent.SUB_TYPE.equalsIgnoreCase(comment.getSubType())) {
            service.sendNotifications(comment.getCommentId());
        }
    }
}