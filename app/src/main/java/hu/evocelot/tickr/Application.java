package hu.evocelot.tickr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hu.evocelot.tickr.service.SchedulerService;

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

    private static final Logger LOG = LogManager.getLogger(Application.class);

    @Autowired
    private SchedulerService schedulerService;

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

    /**
     * CommandLineRunner bean, which will execute the scheduleTasks method
     * when the application starts.
     */
    @Bean
    public CommandLineRunner run() {
        return args -> {
            try {
                schedulerService.scheduleTasks();
                LOG.info("Tasks have been scheduled successfully.");
            } catch (SchedulerException e) {
                LOG.error("Failed to schedule tasks: " + e.getMessage(), e);
            }
        };
    }
}
