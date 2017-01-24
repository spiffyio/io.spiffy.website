package io.spiffy.media.manager;

import java.util.Set;

import javax.inject.Inject;

import com.amazonaws.services.sns.AmazonSNSClient;

import io.spiffy.common.event.MediaDeletedEvent;
import io.spiffy.common.event.MediaPostedEvent;
import io.spiffy.common.event.MediaProcessedEvent;
import io.spiffy.common.manager.SNSManager;

public class MediaSNSManager extends SNSManager {

    public static final String TOPIC = "spiffyio-media";

    @Inject
    public MediaSNSManager(final AmazonSNSClient client) {
        super(client, TOPIC);
    }

    public void publish(final long id) {
        final MediaProcessedEvent event = new MediaProcessedEvent();
        event.setMediaId(id);
        publish(event);
    }

    public void publishPosted(final long id) {
        final MediaPostedEvent event = new MediaPostedEvent();
        event.setMediaId(id);
        publish(event);
    }

    public void publish(final Set<Long> ids) {
        final MediaDeletedEvent event = new MediaDeletedEvent();
        event.setMediaIds(ids);
        publish(event);
    }
}
