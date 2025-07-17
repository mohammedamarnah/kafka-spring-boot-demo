package com.mohammadamarnah.kafka_demo;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for the Kafka Producer.
 *
 * This class provides the necessary beans to configure and create a
 * Kafka ProducerFactory and a KafkaTemplate. The KafkaTemplate provides
 * a high-level abstraction for sending messages to Kafka topics.
 */
@Configuration
public class KafkaProducerConfig {

    /**
     * Injects the Kafka bootstrap server URL from application.properties.
     * Example: kafka.bootstrap-servers=localhost:9092
     */
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Creates a map of producer properties.
     * These properties are used to configure the underlying Kafka producer client.
     *
     * @return A map containing the producer configuration properties.
     */
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // The list of host/port pairs to use for establishing the initial connection to the Kafka cluster.
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Serializer class for key that implements the org.apache.kafka.common.serialization.Serializer interface.
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // Serializer class for value that implements the org.apache.kafka.common.serialization.Serializer interface.
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    /**
     * Creates the Kafka producer factory.
     * The producer factory is responsible for creating producer instances.
     * It's configured with the properties defined in the producerConfigs() method.
     *
     * @return A ProducerFactory instance.
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * Creates the KafkaTemplate.
     * The KafkaTemplate wraps a producer and provides convenience methods for sending data to Kafka topics.
     *
     * @return A KafkaTemplate instance.
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
