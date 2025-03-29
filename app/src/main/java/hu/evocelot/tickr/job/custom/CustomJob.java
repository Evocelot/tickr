package hu.evocelot.tickr.job.custom;

import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import hu.evocelot.tickr.constant.ApplicationConstant;
import hu.evocelot.tickr.job.JobDetails;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

/**
 * A Quartz {@link Job} implementation that logs a predefined message upon
 * execution.
 * 
 * <p>
 * The {@code CustomJob} retrieves a {@link JobDetails} object from the
 * {@link JobExecutionContext}, extracts the {@link CustomJobDetails}, and logs
 * the configured message using Log4j.
 * 
 * <h2>Purpose</h2>
 * This job is designed to handle logging tasks as part of a scheduled workflow.
 * It allows predefined log messages to be written to the application's log
 * system during execution.
 * 
 * @author mark.danisovszky
 */
public class CustomJob implements Job {
    private static final Logger LOG = LogManager.getLogger(CustomJob.class);

    @Autowired
    private Tracer tracer;

    /**
     * Executes the custom job.
     * 
     * <p>
     * Retrieves the {@link JobDetails} from the job data map, extracts the
     * {@link CustomJobDetails}, and logs the configured message.
     * 
     * @param context the Quartz job execution context containing the job details
     *                and data
     * @throws JobExecutionException if the job data map is missing required
     *                               configuration or if an error occurs during
     *                               execution
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Span span = tracer.spanBuilder("CustomJob").startSpan();
        CustomJobDetails taskConfig = (CustomJobDetails) context.getJobDetail().getJobDataMap()
                .get(ApplicationConstant.JOB_DATA_KEY);

        LOG.info(MessageFormat.format("CustomJob executed. Message: {0}", taskConfig.getMessage()));
        span.end();
    }
}
