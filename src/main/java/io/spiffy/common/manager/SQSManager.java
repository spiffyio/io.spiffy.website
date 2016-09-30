package io.spiffy.common.manager;

import lombok.Data;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.spiffy.common.Manager;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.event.Event;
import io.spiffy.common.util.JsonUtil;

public abstract class SQSManager<E extends Event> extends Manager {

    private static final String BASE_URL = "https://sqs.us-west-2.amazonaws.com/509332127709/";

    private static final String URL_TEMPLATE = BASE_URL + "%s" + AppConfig.getSuffix();
    private static final String DLQ_URL_TEMPLATE = BASE_URL + "%s-dlq" + AppConfig.getSuffix();

    private final AmazonSQSClient client;
    private final Class<E> eventClass;
    private final String queue;
    private final String url;
    private final String dlqUrl;

    protected SQSManager(final AmazonSQSClient client, final Class<E> eventClass, final String queue) {
        this.client = client;
        this.eventClass = eventClass;
        this.queue = queue;
        url = String.format(URL_TEMPLATE, queue);
        dlqUrl = String.format(DLQ_URL_TEMPLATE, queue);
    }

    @Scheduled(fixedRate = 10000)
    public void poll() {
        logger.info(String.format("polling from queue: %s...", queue));

        final ReceiveMessageResult result = client.receiveMessage(url);
        final List<Message> messages = result.getMessages();

        logger.info(String.format("retrieved: %s messages...", messages.size()));

        for (final Message message : messages) {
            logger.info(String.format("processing message: %s...", message.getMessageId()));

            final String messageBody = message.getBody();

            final MessageBody body = JsonUtil.deserialize(MessageBody.class, messageBody);
            final String json = body.getMessage() != null ? body.getMessage() : messageBody;

            try {
                final E event = JsonUtil.deserialize(eventClass, json);
                logger.info(String.format("message type: %s, subType: %s...", event.getType(), event.getSubType()));
                process(JsonUtil.deserialize(eventClass, json), json);
                logger.info(String.format("processed message: %s...", message.getMessageId()));
            } catch (final Exception e) {
                logger.warn(String.format("failed to process message: %s...", message.getMessageId()), e);
                client.sendMessage(dlqUrl, message.getBody());
            }

            client.deleteMessage(url, message.getReceiptHandle());
        }
    }

    protected void push(final E event) {
        client.sendMessage(url, JsonUtil.serialize(event));
    }

    protected abstract void process(final E event, final String json);

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MessageBody {
        @JsonProperty("Message")
        private String message;
    }
}
