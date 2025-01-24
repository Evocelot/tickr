package hu.evocelot.tickr.configuration;

/**
 * Represents the configuration for a custom task that logs a message.
 * 
 * @author mark.danisovszky
 */
public class CustomTaskConfig {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
