package io.spiffy.media.manager;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.inject.Inject;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

import io.spiffy.common.Manager;
import io.spiffy.common.config.AppConfig;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class SQSManager extends Manager {

    private static final String URL = "https://sqs.us-west-2.amazonaws.com/509332127709/spiffyio-media" + AppConfig.getSuffix();

    private final AmazonSQSClient client;

    public void poll() {
        final ReceiveMessageResult result = client.receiveMessage(URL);
        final List<Message> messages = result.getMessages();
        for (final Message message : messages) {
            System.out.println(message.getBody());
        }
    }
}
