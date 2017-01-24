package io.spiffy.common.mock;

import java.util.*;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageResult;

import io.spiffy.common.util.UIDUtil;

public class AmazonSQSClientMock extends AmazonSQSClient {

    private static final Map<String, Queue<Message>> queues = new HashMap<>();

    @Override
    public ReceiveMessageResult receiveMessage(final String queueUrl) {
        final Queue<Message> q = getQueue(queueUrl);

        final List<Message> messages = new ArrayList<>();
        while (!q.isEmpty() && messages.size() < 10) {
            messages.add(q.poll());
        }

        final ReceiveMessageResult result = new ReceiveMessageResult();
        result.setMessages(messages);

        return result;
    }

    @Override
    public SendMessageResult sendMessage(final String queueUrl, final String messageBody) {
        send(queueUrl, messageBody);
        return null;
    }

    @Override
    public DeleteMessageResult deleteMessage(final String queueUrl, final String receiptHandle) {
        return null;
    }

    public static void send(final String queueUrl, final String messageBody) {
        final Message message = new Message();
        message.setMessageId(UIDUtil.generateIdempotentId());
        message.setReceiptHandle(UIDUtil.generateIdempotentId());
        message.setBody(messageBody);

        final Queue<Message> q = getQueue(queueUrl);
        q.add(message);
    }

    private static Queue<Message> getQueue(final String queueUrl) {
        final Queue<Message> q;
        synchronized (queues) {
            q = queues.get(queueUrl);
        }

        if (q != null) {
            return q;
        }

        synchronized (queues) {
            queues.put(queueUrl, new PriorityQueue<Message>());
        }
        return getQueue(queueUrl);
    }
}
