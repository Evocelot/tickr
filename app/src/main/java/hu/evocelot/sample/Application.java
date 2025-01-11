package hu.evocelot.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Spring Boot application.
 * <p>
 * This class serves as the starting point for launching the Spring Boot
 * application. It uses the {@link SpringBootApplication} annotation to enable
 * auto-configuration, component scanning, and configuration properties for the
 * application. The {@link #main(String[])} method starts the embedded
 * server and launches the application context.
 * </p>
 * 
 * @author mark.danisovszky
 */
@SpringBootApplication
public class Application {

    /**
     * Main method that initiates the Spring Boot application.
     * <p>
     * This method serves as the entry point to the application. It calls the
     * {@link SpringApplication#run(Class, String...)} method to bootstrap the
     * application, starting the embedded web server (e.g., Tomcat, Jetty) and the
     * Spring application context.
     * </p>
     * 
     * @param args Command line arguments passed to the application at startup.
     * @see SpringApplication#run(Class, String...)
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
