package io.spiffy.common.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishResult;

import io.spiffy.common.config.AppConfig;
import io.spiffy.discussion.manager.DiscussionSNSManager;
import io.spiffy.discussion.manager.DiscussionSQSManager;
import io.spiffy.media.manager.MediaSNSManager;
import io.spiffy.media.manager.MediaSQSManager;
import io.spiffy.notification.manager.NotificationSQSManager;
import io.spiffy.stream.manager.StreamSQSManager;

public class AmazonSNSClientMock extends AmazonSNSClient {

    private static final Map<String, List<String>> subscriptions;

    static {
        subscriptions = new HashMap<>();

        final List<String> discussionSubscriptions = new ArrayList<>();
        discussionSubscriptions.add(DiscussionSQSManager.QUEUE_NAME);
        discussionSubscriptions.add(NotificationSQSManager.QUEUE_NAME);
        subscriptions.put("arn:aws:sns:us-west-2:509332127709:" + DiscussionSNSManager.TOPIC + AppConfig.getSuffix(),
                discussionSubscriptions);

        final List<String> mediaSubscriptions = new ArrayList<>();
        mediaSubscriptions.add(MediaSQSManager.QUEUE_NAME);
        mediaSubscriptions.add(StreamSQSManager.QUEUE_NAME);
        subscriptions.put("arn:aws:sns:us-west-2:509332127709:" + MediaSNSManager.TOPIC + AppConfig.getSuffix(),
                mediaSubscriptions);
    }

    @Override
    public PublishResult publish(final String topicArn, final String message, final String subject) {
        final List<String> queues = subscriptions.get(topicArn);
        for (final String q : queues) {
            AmazonSQSClientMock.send("https://sqs.us-west-2.amazonaws.com/509332127709/" + q + AppConfig.getSuffix(), message);
        }

        return null;
    }
}
