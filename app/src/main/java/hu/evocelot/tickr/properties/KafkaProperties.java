package hu.evocelot.tickr.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Properties class for reading Kafka-related environment variables.
 * 
 * @author mark.danisovszky
 */
@Component
public class KafkaProperties {

    @Value("${KAFKA_ENABLED:false}")
    private String kafkaEnabled;

    @Value("${KAFKA_URL:}")
    private String kafkaUrl;

    @Value("${KAFKA_GROUP_ID:}")
    private String kafkaGroupId;

    public String getKafkaEnabled() {
        return kafkaEnabled;
    }

    public void setKafkaEnabled(String kafkaEnabled) {
        this.kafkaEnabled = kafkaEnabled;
    }

    public String getKafkaUrl() {
        return kafkaUrl;
    }

    public void setKafkaUrl(String kafkaUrl) {
        this.kafkaUrl = kafkaUrl;
    }

    public String getKafkaGroupId() {
        return kafkaGroupId;
    }

    public void setKafkaGroupId(String kafkaGroupId) {
        this.kafkaGroupId = kafkaGroupId;
    }
}