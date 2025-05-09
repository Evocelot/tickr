package hu.evocelot.tickr.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

import hu.evocelot.tickr.configuration.SchedulerConfig;
import hu.evocelot.tickr.constant.ApplicationConstant;
import hu.evocelot.tickr.job.JobDetails;
import hu.evocelot.tickr.job.custom.CustomJob;
import hu.evocelot.tickr.job.custom.CustomJobDetails;
import hu.evocelot.tickr.job.http.HttpJob;
import hu.evocelot.tickr.job.http.HttpJobDetails;
import hu.evocelot.tickr.job.kafka.KafkaProducerJob;
import hu.evocelot.tickr.job.kafka.KafkaProducerJobDetails;

/**
 * Service for scheduling tasks using Quartz Scheduler.
 * <p>
 * This service retrieves task configurations from the {@link SchedulerConfig}
 * and dynamically schedules jobs with their respective triggers based on cron
 * expressions.
 * </p>
 */
@Service
public class SchedulerService {

    private static final Logger LOG = LogManager.getLogger(SchedulerService.class);

    private final Scheduler scheduler;
    private final SchedulerConfig schedulerConfig;

    public SchedulerService(Scheduler scheduler, SchedulerConfig schedulerConfig) {
        this.scheduler = scheduler;
        this.schedulerConfig = schedulerConfig;
    }

    /**
     * Schedules all tasks defined in the application configuration.
     * <p>
     * For each task, a {@link JobDetail} is created and populated with the task's
     * configuration. A corresponding {@link Trigger} is created using the task's
     * cron expression. These are then registered with the Quartz {@link Scheduler}.
     * </p>
     *
     * @throws SchedulerException if there is an issue with scheduling a job or
     *                            trigger
     */
    public void scheduleTasks() throws SchedulerException {
        // Retrieve all task configurations
        List<JobDetails> tasks = schedulerConfig.getTasks();

        for (JobDetails task : tasks) {
            // Create a JobDetail for the task
            JobDetail jobDetail = null;

            if (Objects.nonNull(task.getHttp())) {
                HttpJobDetails httpConfig = (HttpJobDetails) task.getHttp();
                jobDetail = JobBuilder.newJob(HttpJob.class)
                        .withIdentity(task.getName())
                        .build();
                jobDetail.getJobDataMap().put(ApplicationConstant.JOB_DATA_KEY, httpConfig);
            } else if (Objects.nonNull(task.getCustom())) {
                CustomJobDetails logTaskConfig = (CustomJobDetails) task.getCustom();
                jobDetail = JobBuilder.newJob(CustomJob.class)
                        .withIdentity(task.getName())
                        .build();
                jobDetail.getJobDataMap().put(ApplicationConstant.JOB_DATA_KEY, logTaskConfig);
            } else if (Objects.nonNull(task.getKafkaProducer())) {
                KafkaProducerJobDetails kafkaProducerTaskConfig = (KafkaProducerJobDetails) task.getKafkaProducer();
                jobDetail = JobBuilder.newJob(KafkaProducerJob.class).withIdentity(task.getName()).build();
                jobDetail.getJobDataMap().put(ApplicationConstant.JOB_DATA_KEY, kafkaProducerTaskConfig);
            } else {
                throw new UnsupportedOperationException("The job type is not supported!");
            }

            // Create a Trigger with the task's cron schedule
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(task.getName() + "Trigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getCron()))
                    .build();

            // Schedule the job with the scheduler
            scheduler.scheduleJob(jobDetail, trigger);

            LOG.info(MessageFormat.format("Task with name {0} have been scheduled successfully.", task.getName()));
        }
    }
}
