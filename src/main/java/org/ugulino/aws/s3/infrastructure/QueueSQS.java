package org.ugulino.aws.s3.infrastructure;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class QueueSQS {

    public QueueSQS() {
    }

    public void sentToQueue(String message, String queueName) {
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        String queueUrl = sqs.getQueueUrl(queueName).getQueueUrl();
         try {
             final SendMessageRequest sendMessageRequest = new SendMessageRequest()
                     .withQueueUrl(queueUrl)
                     .withMessageBody(message);

             sendMessageRequest.setMessageGroupId("1");
             sqs.sendMessage(sendMessageRequest);
         } catch (Exception e) {
             throw new AmazonSQSException("Fail send message!");
         }
    }
}
