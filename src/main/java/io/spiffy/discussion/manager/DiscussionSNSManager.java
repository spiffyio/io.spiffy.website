package io.spiffy.discussion.manager;

import java.util.Set;

import javax.inject.Inject;

import com.amazonaws.services.sns.AmazonSNSClient;

import io.spiffy.common.event.CommentNotificationEvent;
import io.spiffy.common.event.CommentPostedEvent;
import io.spiffy.common.manager.SNSManager;

public class DiscussionSNSManager extends SNSManager {

    public static final String TOPIC = "spiffyio-discussion";

    @Inject
    public DiscussionSNSManager(final AmazonSNSClient client) {
        super(client, TOPIC);
    }

    public void publish(final long commentId) {
        final CommentPostedEvent event = new CommentPostedEvent();
        event.setCommentId(commentId);
        publish(event);
    }

    public void publish(final long commentId, final long postId, final Set<Long> subscriberIds) {
        final CommentNotificationEvent event = new CommentNotificationEvent();
        event.setCommentId(commentId);
        event.setPostId(postId);
        event.setSubscriberIds(subscriberIds);
        publish(event);
    }
}