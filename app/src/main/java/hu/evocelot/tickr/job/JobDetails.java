package hu.evocelot.tickr.job;

import java.util.Objects;

import hu.evocelot.tickr.job.custom.CustomJobDetails;
import hu.evocelot.tickr.job.http.HttpJobDetails;
import hu.evocelot.tickr.job.kafka.KafkaProducerJobDetails;

/**
 * Represents the details for a single job in the system.
 * 
 * <p>
 * This class allows defining job-specific properties such as the task's name,
 * schedule, and type. Additionally, a task can have either an
 * {@link HttpJobDetails} or a {@link CustomJobDetails} associated with it, but
 * not both at the same time. Attempting to set both configurations will result
 * in an {@link IllegalStateException}.
 * 
 * @author mark.danisovszky
 */
public class JobDetails {

    private String name;
    private String cron;
    private String type;
    private HttpJobDetails http;
    private CustomJobDetails custom;
    private KafkaProducerJobDetails kafkaProducer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HttpJobDetails getHttp() {
        return http;
    }

    public void setHttp(HttpJobDetails http) {
        if (Objects.nonNull(custom) || Objects.nonNull(kafkaProducer)) {
            throw new IllegalStateException();
        }

        this.http = http;
    }

    public CustomJobDetails getCustom() {
        return custom;
    }

    public void setCustom(CustomJobDetails custom) {
        if (Objects.nonNull(http) || Objects.nonNull(kafkaProducer)) {
            throw new IllegalStateException();
        }

        this.custom = custom;
    }

    public KafkaProducerJobDetails getKafkaProducer() {
        return kafkaProducer;
    }

    public void setKafkaProducer(KafkaProducerJobDetails kafkaProducer) {
        if (Objects.nonNull(http) || Objects.nonNull(custom)) {
            throw new IllegalStateException();
        }

        this.kafkaProducer = kafkaProducer;
    }
}
