package hu.evocelot.tickr.job;

import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hu.evocelot.tickr.configuration.HttpTaskConfig;
import hu.evocelot.tickr.configuration.TaskConfig;
import hu.evocelot.tickr.constant.ApplicationConstant;

/**
 * Represents a Quartz job responsible for executing HTTP requests.
 * <p>
 * This job retrieves HTTP configuration details from the associated Quartz
 * {@link org.quartz.JobDataMap} and performs an HTTP request using the
 * configured method, URL, and body. The results of the request are logged for
 * monitoring and debugging purposes.
 * </p>
 */
@Component
public class HttpJob implements Job {

    private static final Logger LOG = LogManager.getLogger(HttpJob.class);

    private final RestTemplate restTemplate;

    /**
     * Constructs an instance of {@code HttpJob} and initializes a
     * {@link RestTemplate} for executing HTTP requests.
     */
    public HttpJob() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Executes the HTTP request as defined in the Quartz job configuration.
     * <p>
     * The method extracts the {@link TaskConfig} object from the
     * {@link JobExecutionContext}, retrieves the HTTP method, URL, and request body
     * from the {@link HttpTaskConfig}, and uses a {@link RestTemplate} to perform
     * the
     * HTTP request. Details of the request are logged after successful execution.
     * </p>
     *
     * @param context the execution context containing details about the job and its
     *                runtime environment
     * @throws RuntimeException if the HTTP method or configuration is invalid, or
     *                          the request fails
     */
    @Override
    public void execute(JobExecutionContext context) {
        HttpTaskConfig httpTaskConfig = (HttpTaskConfig) context.getJobDetail().getJobDataMap()
                .get(ApplicationConstant.JOB_DATA_KEY);

        // Extract HTTP configuration details
        HttpMethod httpMethod = HttpMethod.valueOf(httpTaskConfig.getMethod().toUpperCase());
        HttpEntity<String> request = new HttpEntity<>(httpTaskConfig.getBody());

        // Perform the HTTP request
        restTemplate.exchange(httpTaskConfig.getUrl(), httpMethod, request, String.class);

        // Log the execution details
        LOG.info(MessageFormat.format("HTTP Request executed. Method: {0} | Url: {1} | body: {2}",
                httpTaskConfig.getMethod(), httpTaskConfig.getUrl(), httpTaskConfig.getBody()));
    }
}
