package hu.evocelot.sample.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.evocelot.sample.controller.sample.SampleController;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.swagger.v3.oas.annotations.Operation;

/**
 * Test RestController.
 * 
 * @author mark.danisovszky
 */
@RestController
public class TestController {

	private static final Logger LOG = LogManager.getLogger(SampleController.class);

	public TestController(MeterRegistry meterRegistry) {
		// Create custom application metric.
		this.counter = Counter.builder("test_endpoint_called")
				.description("The total number of the test endpoind call")
				.register(meterRegistry);
	}

	@Autowired
	private Tracer tracer;

	private Counter counter;

	/**
	 * Test root endpoint.
	 * 
	 * @return - the "Hello World" string
	 */
	@GetMapping("/")
	@Operation(summary = "Test endpoint", description = "Endpoint for testing tracing, logging and metics.")
	public String helloWorld() {
		Span span = tracer.spanBuilder("sampleMethodSpan").startSpan();

		LOG.log(Level.INFO, "sample endpoint triggered");

		counter.increment();

		span.end();

		return "Hello world!";
	}
}
