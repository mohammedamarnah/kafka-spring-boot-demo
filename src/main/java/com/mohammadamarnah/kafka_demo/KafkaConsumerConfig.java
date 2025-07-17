package com.mohammadamarnah.kafka_demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for the Kafka Consumer.
 *
 * This class sets up the necessary beans to configure and create
 * a Kafka consumer factory and a listener container factory.
 * These factories are used by Spring Kafka to create consumers
 * that listen for messages on specified topics.
 */
@Configuration
public class KafkaConsumerConfig {

    /**
     * Injects the Kafka bootstrap server URL from the application.properties file.
     * It's good practice to externalize this configuration.
     * Example in application.properties: kafka.bootstrap-servers=localhost:9092
     */
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Injects the consumer group ID from the application.properties file.
     * Consumers with the same group ID will be part of the same consumer group.
     * Example in application.properties: kafka.consumer.group-id=myGroup
     */
    @Value("${kafka.consumer.group-id}")
    private String groupId;

    /**
     * Creates a map of consumer properties.
     * These properties are used to configure the underlying Kafka consumer client.
     *
     * @return A map containing the consumer configuration properties.
     */
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // The list of host/port pairs to use for establishing the initial connection to the Kafka cluster.
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // A unique string that identifies the consumer group this consumer belongs to.
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        // Deserializer class for key that implements the org.apache.kafka.common.serialization.Deserializer interface.
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // Deserializer class for value that implements the org.apache.kafka.common.serialization.Deserializer interface.
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // What to do when there is no initial offset in Kafka or if the current offset does not exist any more on the server.
        // 'earliest': automatically reset the offset to the earliest offset.
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    /**
     * Creates the Kafka consumer factory.
     * The consumer factory is responsible for creating consumer instances.
     * It's configured with the properties defined in the consumerConfigs() method.
     *
     * @return A ConsumerFactory instance.
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    /**
     * Creates the Kafka listener container factory.
     * This factory is used by the @KafkaListener annotation to create the message listener container.
     * It's configured with the consumer factory.
     *
     * @return A ConcurrentKafkaListenerContainerFactory instance.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
