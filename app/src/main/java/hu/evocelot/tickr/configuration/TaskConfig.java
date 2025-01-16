package hu.evocelot.tickr.configuration;

import java.util.Objects;

/**
 * Represents the configuration for a single task in the system.
 * 
 * <p>
 * This class allows defining task-specific properties such as the task's name,
 * schedule, and type. Additionally, a task can have either an
 * {@link HttpTaskConfig} or a {@link CustomTaskConfig} associated with it, but
 * not both at the same time. Attempting to set both configurations will result
 * in an {@link IllegalStateException}.
 * 
 * @author mark.danisovszky
 */
public class TaskConfig {

    private String name;
    private String cron;
    private String type;
    private HttpTaskConfig http;
    private CustomTaskConfig custom;

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

    public HttpTaskConfig getHttp() {
        return http;
    }

    public void setHttp(HttpTaskConfig http) {
        if (Objects.nonNull(custom)) {
            throw new IllegalStateException();
        }

        this.http = http;
    }

    public CustomTaskConfig getCustom() {
        return custom;
    }

    public void setCustom(CustomTaskConfig custom) {
        if (Objects.nonNull(http)) {
            throw new IllegalStateException();
        }

        this.custom = custom;
    }
}
