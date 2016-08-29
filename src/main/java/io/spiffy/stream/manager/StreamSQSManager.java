package io.spiffy.stream.manager;

import javax.inject.Inject;

import com.amazonaws.services.sqs.AmazonSQSClient;

import io.spiffy.common.event.MediaProcessedEvent;
import io.spiffy.common.manager.SQSManager;
import io.spiffy.stream.service.PostService;

public class StreamSQSManager extends SQSManager<MediaProcessedEvent> {

    private static final String QUEUE_NAME = "spiffyio-stream";

    private final PostService service;

    @Inject
    public StreamSQSManager(final AmazonSQSClient client, final PostService service) {
        super(client, MediaProcessedEvent.class, QUEUE_NAME);
        this.service = service;
    }

    public void process(final MediaProcessedEvent content) {
        service.processByMediaId(content.getMediaId());
    }
}