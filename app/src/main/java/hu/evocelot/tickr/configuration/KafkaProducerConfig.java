package hu.evocelot.tickr.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import hu.evocelot.tickr.properties.KafkaProperties;

/**
 * Configuration class responsible for creating Kafka producer beans used for
 * sending messages to Kafka topics.
 * <p>
 * This class defines and configures two key beans:
 * <ul>
 * <li>{@link ProducerFactory}: Creates Kafka producers with the necessary
 * configuration to serialize message keys and values.</li>
 * <li>{@link KafkaTemplate}: A higher-level abstraction that simplifies sending
 * messages using Kafka producers.</li>
 * </ul>
 * </p>
 * <p>
 * The configuration relies on the {@link KafkaProperties} class to retrieve
 * Kafka-specific properties such as the Kafka URL and serialization settings,
 * providing flexibility to configure Kafka connections.
 * </p>
 * 
 * @author mark.danisovszky
 */
@Configuration
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaProducerConfig {

    private final KafkaProperties kafkaProperties;

    public KafkaProducerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    /**
     * Creates and configures a {@link ProducerFactory} bean. The producer factory
     * is responsible for creating Kafka producers that serialize both the key and
     * value of the messages before sending them to Kafka topics.
     * <p>
     * This configuration specifies the necessary Kafka connection details, such as
     * the Kafka URL, and the serializer classes for both the key and value. The
     * producer factory is used by {@link KafkaTemplate} to send messages.
     * </p>
     * 
     * @return a configured {@link ProducerFactory} instance that can be used to
     *         create Kafka producers.
     */
    @Bean
    ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        // Set the Kafka URL for the producer to connect to
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getUrl());

        // Set the serializer for both key and value of messages as StringSerializer
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // Return the configured ProducerFactory that will create Kafka producers
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Creates and configures a {@link KafkaTemplate} bean. KafkaTemplate is a
     * high-level abstraction for sending messages to Kafka topics, utilizing the
     * configured
     * {@link ProducerFactory}.
     * <p>
     * The KafkaTemplate simplifies the process of producing messages to Kafka,
     * allowing users to send messages to specific topics with minimal boilerplate
     * code.
     * </p>
     * 
     * @return a configured {@link KafkaTemplate} instance that can be used to send
     *         messages to Kafka topics.
     */
    @Bean
    KafkaTemplate<String, String> kafkaTemplate() {
        // Create and return a new KafkaTemplate using the producer factory
        return new KafkaTemplate<>(producerFactory());
    }
}