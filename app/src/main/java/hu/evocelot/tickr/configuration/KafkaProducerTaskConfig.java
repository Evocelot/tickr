package hu.evocelot.tickr.configuration;

/**
 * Represents the kafka producer configuration for a task, including the topic
 * and the message to send.
 * 
 * @author mark.danisovszky
 */
public class KafkaProducerTaskConfig {
    private String topic;
    private String message;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
