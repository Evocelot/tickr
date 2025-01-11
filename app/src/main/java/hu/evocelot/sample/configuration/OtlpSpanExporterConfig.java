package hu.evocelot.sample.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hu.evocelot.sample.properties.JaegerProperties;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;

/**
 * Configuration class responsible for setting up the OTLP GRPC Span Exporter
 * used for exporting trace data to a Jaeger backend via the OpenTelemetry
 * Protocol (OTLP).
 * <p>
 * This class defines a single {@link OtlpGrpcSpanExporter} bean, which is used
 * to send telemetry data from the application to a Jaeger tracing backend,
 * helping with distributed tracing and performance monitoring.
 * </p>
 * <p>
 * The exporter is configured with the tracing URL provided by the
 * {@link JaegerProperties} class,
 * which holds the necessary configuration details for the tracing system.
 * </p>
 * 
 * @author mark.danisovszky
 */
@Configuration
public class OtlpSpanExporterConfig {

    @Autowired
    private JaegerProperties jaegerProperties;

    /**
     * Creates and configures the {@link OtlpGrpcSpanExporter} bean.
     * The OTLP GRPC Span Exporter is responsible for exporting trace data to the
     * Jaeger backend via the OpenTelemetry Protocol (OTLP).
     * <p>
     * The exporter is configured with the tracing URL that points to the Jaeger
     * service, allowing the application to send telemetry data for performance
     * monitoring and troubleshooting.
     * </p>
     * 
     * @return a configured {@link OtlpGrpcSpanExporter} instance that sends trace
     *         data to the Jaeger backend.
     */
    @Bean
    OtlpGrpcSpanExporter otlpGrpcSpanExporter() {
        // Create and return the OtlpGrpcSpanExporter, configured with the tracing URL
        // from JaegerProperties
        return OtlpGrpcSpanExporter.builder()
                .setEndpoint(jaegerProperties.getTracingUrl())
                .build();
    }
}
