package hu.evocelot.tickr.service;

import java.util.List;
import java.util.Objects;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.evocelot.tickr.configuration.HttpTaskConfig;
import hu.evocelot.tickr.configuration.LogTaskConfig;
import hu.evocelot.tickr.configuration.SchedulerConfig;
import hu.evocelot.tickr.configuration.TaskConfig;
import hu.evocelot.tickr.constant.ApplicationConstant;
import hu.evocelot.tickr.job.HttpJob;
import hu.evocelot.tickr.job.LogJob;

/**
 * Service for scheduling tasks using Quartz Scheduler.
 * <p>
 * This service retrieves task configurations from the {@link SchedulerConfig}
 * and dynamically schedules jobs with their respective triggers based on cron
 * expressions. Each task is mapped to an {@link HttpJob} instance,
 * which
 * executes the specified HTTP requests.
 * </p>
 */
@Service
public class SchedulerService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SchedulerConfig schedulerConfig;

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
        List<TaskConfig> tasks = schedulerConfig.getTasks();

        for (TaskConfig task : tasks) {
            // Create a JobDetail for the task
            JobDetail jobDetail = null;

            if (Objects.nonNull(task.getHttp())) {
                HttpTaskConfig httpConfig = (HttpTaskConfig) task.getHttp();
                jobDetail = JobBuilder.newJob(HttpJob.class)
                        .withIdentity(task.getName())
                        .build();
                jobDetail.getJobDataMap().put(ApplicationConstant.JOB_DATA_KEY, httpConfig);
            } else if (Objects.nonNull(task.getLog())) {
                LogTaskConfig logTaskConfig = (LogTaskConfig) task.getLog();
                jobDetail = JobBuilder.newJob(LogJob.class)
                        .withIdentity(task.getName())
                        .build();
                jobDetail.getJobDataMap().put(ApplicationConstant.JOB_DATA_KEY, logTaskConfig);
            }

            // Create a Trigger with the task's cron schedule
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(task.getName() + "Trigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getCron()))
                    .build();

            // Schedule the job with the scheduler
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }
}
