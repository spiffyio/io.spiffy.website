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
    private final String url;
    private final String dlqUrl;

    protected SQSManager(final AmazonSQSClient client, final Class<E> eventClass, final String queue) {
        this.client = client;
        this.eventClass = eventClass;
        url = String.format(URL_TEMPLATE, queue);
        dlqUrl = String.format(DLQ_URL_TEMPLATE, queue);
    }

    @Scheduled(fixedRate = 10000)
    public void poll() {
        final ReceiveMessageResult result = client.receiveMessage(url);
        final List<Message> messages = result.getMessages();
        for (final Message message : messages) {
            final MessageBody body = JsonUtil.deserialize(MessageBody.class, message.getBody());
            final String json = body.getMessage();

            try {
                process(JsonUtil.deserialize(eventClass, json), json);
            } catch (final Exception e) {
                client.sendMessage(dlqUrl, message.getBody());
            }

            client.deleteMessage(url, message.getReceiptHandle());
        }
    }

    protected abstract void process(final E event, final String json);

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MessageBody {
        @JsonProperty("Message")
        private String message;
    }
}
