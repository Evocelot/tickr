package hu.evocelot.tickr.job;

import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.evocelot.tickr.configuration.KafkaProducerTaskConfig;
import hu.evocelot.tickr.constant.ApplicationConstant;
import hu.evocelot.tickr.kafka.KafkaMessageProducer;

/**
 * Represents a Quartz {@link Job} implementation responsible for sending
 * messages to a Kafka topic.
 * <p>
 * <strong>Note:</strong> The {@code KafkaMessageProducer} is injected
 * conditionally (required = false),
 * allowing the job to operate without failing in environments where a Kafka
 * producer is not available.
 * </p>
 * 
 * @author mark.danisovszky
 */
@Component
public class KafkaProducerJob implements Job {

    private static final Logger LOG = LogManager.getLogger(KafkaProducerJob.class);

    @Autowired(required = false)
    private KafkaMessageProducer kafkaMessageProducer;

    @Override
    public void execute(JobExecutionContext context) {
        KafkaProducerTaskConfig kafkaProducerTaskConfig = (KafkaProducerTaskConfig) context.getJobDetail()
                .getJobDataMap()
                .get(ApplicationConstant.JOB_DATA_KEY);

        kafkaMessageProducer.sendMessage(kafkaProducerTaskConfig.getTopic(), kafkaProducerTaskConfig.getMessage());

        LOG.info(MessageFormat.format("Kafka producer job executed. Topics: {0} | Message: {1}",
                kafkaProducerTaskConfig.getTopic(), kafkaProducerTaskConfig.getMessage()));
    }
}
