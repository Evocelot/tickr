package hu.evocelot.tickr.job.custom;

/**
 * Represents the details for a custom task that logs a message.
 * 
 * @author mark.danisovszky
 */
public class CustomJobDetails {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
