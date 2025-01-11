package hu.evocelot.sample.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
@Component
public class JaegerProperties {

    @Value("${TRACING_URL}")
    private String tracingUrl;

    public String getTracingUrl() {
        return tracingUrl;
    }

    public void setTracingUrl(String tracingUrl) {
        this.tracingUrl = tracingUrl;
    }
}
