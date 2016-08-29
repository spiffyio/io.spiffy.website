package io.spiffy.media.manager;

import javax.inject.Inject;

import com.amazonaws.services.sqs.AmazonSQSClient;

import io.spiffy.common.event.MediaProcessedEvent;
import io.spiffy.common.manager.SQSManager;
import io.spiffy.media.service.ContentService;

public class MediaSQSManager extends SQSManager<MediaProcessedEvent> {

    private static final String QUEUE_NAME = "spiffyio-media";

    private final ContentService service;

    @Inject
    public MediaSQSManager(final AmazonSQSClient client, final ContentService service) {
        super(client, MediaProcessedEvent.class, QUEUE_NAME);
        this.service = service;
    }

    public void process(final MediaProcessedEvent content) {
        service.process(content.getMediaId());
    }
}