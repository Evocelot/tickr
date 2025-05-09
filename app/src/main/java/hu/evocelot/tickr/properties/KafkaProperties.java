package hu.evocelot.tickr.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Properties class for reading Kafka-related environment variables.
 * 
 * @author mark.danisovszky
 */
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    private String enabled;
    private String url;
    private String groupId;

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String kafkaEnabled) {
        this.enabled = kafkaEnabled;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String kafkaUrl) {
        this.url = kafkaUrl;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String kafkaGroupId) {
        this.groupId = kafkaGroupId;
    }
}