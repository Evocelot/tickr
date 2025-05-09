package hu.evocelot.tickr.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Properties class for reading Jaeger-related environment variables.
 * <p>
 * This class is used to load and provide access to the Jaeger tracing
 * configuration, specifically the URL used for tracing. The value is injected
 * from the environment variables.
 * </p>
 * 
 * @author mark.danisovszky
 */
@Configuration
@ConfigurationProperties(prefix = "tracing")
public class JaegerProperties {

    private String url;
    private String enabled;

    public String getUrl() {
        return url;
    }

    public void setUrl(String tracingUrl) {
        this.url = tracingUrl;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String tracingEnabled) {
        this.enabled = tracingEnabled;
    }
}
