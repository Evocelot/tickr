package hu.evocelot.tickr.job;

import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import hu.evocelot.tickr.configuration.LogTaskConfig;
import hu.evocelot.tickr.configuration.TaskConfig;
import hu.evocelot.tickr.constant.ApplicationConstant;

/**
 * A Quartz {@link Job} implementation that logs a predefined message upon
 * execution.
 * 
 * <p>
 * The {@code LogJob} retrieves a {@link TaskConfig} object from the
 * {@link JobExecutionContext}, extracts the {@link LogTaskConfig}, and logs the
 * configured message using Log4j.
 * 
 * <h2>Purpose</h2>
 * This job is designed to handle logging tasks as part of a scheduled workflow.
 * It allows predefined log messages to be written to the application's log
 * system during execution.
 * 
 * @author mark.danisovszky
 */
public class LogJob implements Job {
    private static final Logger LOG = LogManager.getLogger(LogJob.class);

    /**
     * Executes the logging job.
     * 
     * <p>
     * Retrieves the {@link TaskConfig} from the job data map, extracts the
     * {@link LogTaskConfig}, and logs the configured message.
     * 
     * @param context the Quartz job execution context containing the job details
     *                and data
     * @throws JobExecutionException if the job data map is missing required
     *                               configuration or
     *                               if an error occurs during execution
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LogTaskConfig taskConfig = (LogTaskConfig) context.getJobDetail().getJobDataMap()
                .get(ApplicationConstant.JOB_DATA_KEY);

        LOG.info(MessageFormat.format("LogJob executed. Message: {0}", taskConfig.getMessage()));
    }
}
