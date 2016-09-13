package io.spiffy.common.manager;

import com.amazonaws.services.sns.AmazonSNSClient;

import io.spiffy.common.Manager;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.event.Event;
import io.spiffy.common.util.JsonUtil;

public abstract class SNSManager extends Manager {

    private final AmazonSNSClient client;
    private final String topic;

    protected SNSManager(final AmazonSNSClient client, final String topic) {
        this.client = client;
        this.topic = "arn:aws:sns:us-west-2:509332127709:" + topic + AppConfig.getSuffix();
    }

    protected void publish(final Event event) {
        client.publish(topic, JsonUtil.serialize(event), event.getType() + ":" + event.getSubType());
    }
}