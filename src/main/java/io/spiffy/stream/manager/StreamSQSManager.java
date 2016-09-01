package io.spiffy.stream.manager;

import javax.inject.Inject;

import com.amazonaws.services.sqs.AmazonSQSClient;

import io.spiffy.common.event.MediaDeletedEvent;
import io.spiffy.common.event.MediaEvent;
import io.spiffy.common.event.MediaProcessedEvent;
import io.spiffy.common.manager.SQSManager;
import io.spiffy.stream.service.PostService;

public class StreamSQSManager extends SQSManager<MediaEvent> {

    private static final String QUEUE_NAME = "spiffyio-stream";

    private final PostService service;

    @Inject
    public StreamSQSManager(final AmazonSQSClient client, final PostService service) {
        super(client, MediaEvent.class, QUEUE_NAME);
        this.service = service;
    }

    public void process(final MediaEvent content, final String json) {
        if (MediaProcessedEvent.SUB_TYPE.equalsIgnoreCase(content.getSubType())) {
            service.processByMediaId(content.getMediaId());
        } else if (MediaDeletedEvent.SUB_TYPE.equalsIgnoreCase(content.getSubType())) {
            service.deleteByMediaIds(content.getMediaIds());
        }
    }
}