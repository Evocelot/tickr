package hu.evocelot.sample.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hu.evocelot.sample.interceptor.LoggingInterceptor;

/**
 * Configuration class for customizing the Spring MVC setup, specifically for
 * registering interceptors that handle specific request/response behaviors.
 * <p>
 * In this case, the class configures the {@link LoggingInterceptor} to be
 * applied globally to all incoming HTTP requests. The interceptor is
 * responsible for logging relevant details about each HTTP request and
 * response, which is useful for debugging, monitoring, and auditing purposes.
 * </p>
 * 
 * @author mark.danisovszky
 */
@Component
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoggingInterceptor loggingInterceptor;

    /**
     * Registers the {@link LoggingInterceptor} to handle incoming HTTP requests and
     * responses.
     * <p>
     * The {@link LoggingInterceptor} will log information about requests and
     * responses for better visibility into the application's behavior. This can be
     * particularly helpful in tracking the flow of requests through the system,
     * monitoring performance, and ensuring proper request handling.
     * </p>
     * 
     * @param registry the {@link InterceptorRegistry} to which interceptors can be
     *                 added
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Adding the LoggingInterceptor to the registry to capture logs for all HTTP
        // requests and responses.
        registry.addInterceptor(loggingInterceptor);
    }
}