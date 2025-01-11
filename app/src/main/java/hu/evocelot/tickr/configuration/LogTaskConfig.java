package hu.evocelot.tickr.configuration;

/**
 * Represents the configuration for a task that logs a message.
 * 
 * @see TaskDetails
 */
public class LogTaskConfig {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
