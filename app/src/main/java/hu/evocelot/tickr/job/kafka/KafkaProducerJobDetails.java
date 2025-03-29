package hu.evocelot.tickr.job.kafka;

/**
 * Represents the kafka producer details for a task, including the topic
 * and the message to send.
 * 
 * @author mark.danisovszky
 */
public class KafkaProducerJobDetails {
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
