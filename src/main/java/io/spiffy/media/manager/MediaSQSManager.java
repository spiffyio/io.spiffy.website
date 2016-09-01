package io.spiffy.media.manager;

import javax.inject.Inject;

import com.amazonaws.services.sqs.AmazonSQSClient;

import io.spiffy.common.event.MediaDeletedEvent;
import io.spiffy.common.event.MediaEvent;
import io.spiffy.common.event.MediaProcessedEvent;
import io.spiffy.common.manager.SQSManager;
import io.spiffy.media.service.ContentService;

public class MediaSQSManager extends SQSManager<MediaEvent> {

    private static final String QUEUE_NAME = "spiffyio-media";

    private final ContentService service;

    @Inject
    public MediaSQSManager(final AmazonSQSClient client, final ContentService service) {
        super(client, MediaEvent.class, QUEUE_NAME);
        this.service = service;
    }

    public void process(final MediaEvent content, final String json) {
        if (MediaProcessedEvent.SUB_TYPE.equalsIgnoreCase(content.getSubType())) {
            service.process(content.getMediaId());
        } else if (MediaDeletedEvent.SUB_TYPE.equalsIgnoreCase(content.getSubType())) {
            for (final long id : content.getMediaIds()) {
                service.delete(id);
            }
        }
    }
}