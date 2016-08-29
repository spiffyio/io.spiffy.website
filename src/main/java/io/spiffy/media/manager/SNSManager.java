package io.spiffy.media.manager;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import com.amazonaws.services.sns.AmazonSNSClient;

import io.spiffy.common.Manager;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.event.Event;
import io.spiffy.common.event.MediaProcessedEvent;
import io.spiffy.common.util.JsonUtil;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SNSManager extends Manager {

    private static final String TOPIC = "arn:aws:sns:us-west-2:509332127709:spiffyio-media" + AppConfig.getSuffix();

    private final AmazonSNSClient client;

    public void publish(final long id) {
        final MediaProcessedEvent event = new MediaProcessedEvent();
        event.setMediaId(id);
        publish(event);
    }

    private void publish(final Event event) {
        client.publish(TOPIC, JsonUtil.serialize(event), event.getType() + ":" + event.getSubType());
    }
}