package com.mohammadamarnah.kafka_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * A sample Kafka consumer class.
 *
 * This class listens for messages from a specified Kafka topic and
 * processes them. The @Component annotation allows Spring to detect
 * this class and manage it as a bean.
 */
@Component
public class SampleKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SampleKafkaConsumer.class);

    /**
     * Listens for messages on the "sample-topic".
     *
     * The @KafkaListener annotation marks this method as the target for
     * receiving messages from Kafka. The 'topics' attribute specifies which
     * topic to listen to, and the 'groupId' attribute specifies the consumer
     * group this listener belongs to.
     *
     * @param message The message received from the Kafka topic.
     */
    @KafkaListener(topics = "${kafka.sample-topic}", groupId = "${kafka.consumer.group-id}")
    public void listen(String message) {
        // Log the received message. In a real application, you would
        // put your message processing logic here.
        logger.info("Received message in group's consumer: {}", message);
    }
}
