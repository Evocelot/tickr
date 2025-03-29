package hu.evocelot.tickr.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka message producer responsible for sending messages to Kafka topics.
 * <p>
 * This component uses {@link KafkaTemplate} to send messages to specified Kafka
 * topics. It provides a simple method to publish messages, which can be
 * consumed by other services or components listening on the same topic. It also
 * logs the sent messages for monitoring and debugging purposes.
 * </p>
 * 
 * @author mark.danisovszky
 */
@ConditionalOnProperty(name = "KAFKA_ENABLED", havingValue = "true", matchIfMissing = false)
@Component
public class KafkaMessageProducer {

    private static final Logger LOG = LogManager.getLogger(KafkaMessageProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Sends a message to the specified Kafka topic.
     * <p>
     * This method uses the injected {@link KafkaTemplate} to send the provided
     * message to the specified Kafka topic. After the message is sent, it logs the
     * message for monitoring purposes.
     * </p>
     *
     * @param topic   - The Kafka topic to which the message should be sent.
     * @param message - The message to be sent to the Kafka topic. The message is
     *                expected to be a string.
     */
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        LOG.info("Kafka message sent: " + message);
    }
}