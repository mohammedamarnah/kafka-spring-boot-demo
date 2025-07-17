package com.mohammadamarnah.kafka_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * A sample Kafka producer class.
 *
 * This class acts as a service that can be used to send messages
 * to a Kafka topic. The @Service annotation allows Spring to detect
 * this class and manage it as a bean.
 */
@Service
public class SampleKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(SampleKafkaProducer.class);

    @Value("${kafka.sample-topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Sends a message to the "sample-topic" Kafka topic.
     *
     * @param message The message to be sent.
     */
    public void sendMessage(String message) {
        logger.info("Producing message: {}", message);
        // The send method returns a CompletableFuture. You can add callbacks
        // to handle success or failure if needed.
        this.kafkaTemplate.send(topic, message);
    }
}
