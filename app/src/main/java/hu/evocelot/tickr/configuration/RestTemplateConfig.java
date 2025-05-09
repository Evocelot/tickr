package hu.evocelot.tickr.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for defining HTTP client beans used across the
 * application.
 * <p>
 * Provides a singleton {@link RestTemplate} bean that can be injected wherever
 * HTTP communication is required, such as service layers or scheduled jobs.
 * </p>
 * 
 * @author mark.danisovszky
 */
@Configuration
public class RestTemplateConfig {
    /**
     * Creates a {@link RestTemplate} bean.
     *
     * @return a default configured RestTemplate instance
     */
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
