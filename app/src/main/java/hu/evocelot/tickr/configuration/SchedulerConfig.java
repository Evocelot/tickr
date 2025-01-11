package hu.evocelot.tickr.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration class for the scheduler.
 * <p>
 * This class is designed to map the `scheduler` section from the application's
 * configuration file (YAML or properties).
 * It provides a structured representation of scheduled tasks and their
 * associated configurations.
 * </p>
 * <p>
 * Example configuration in YAML:
 * 
 * <pre>
 * scheduler:
 *   tasks:
 *     - name: sendHttpRequestTask
 *       cron: "0 * * * * ?"
 *       http:
 *         method: POST
 *         url: http://example:8080/example
 *         body: null
 *     - name: printTestLogTask
 *       cron: "0 * * * * ?"
 *       log:
 *         message: "Test message"
 * </pre>
 * </p>
 * 
 * @author mark.danisovszky
 */
@Component
@ConfigurationProperties(prefix = "scheduler")
public class SchedulerConfig {
    private List<TaskConfig> tasks;

    public List<TaskConfig> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskConfig> tasks) {
        this.tasks = tasks;
    }
}
