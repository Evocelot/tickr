package hu.evocelot.job;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import hu.evocelot.tickr.constant.ApplicationConstant;
import hu.evocelot.tickr.job.http.HttpJob;
import hu.evocelot.tickr.job.http.HttpJobDetails;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;

class HttpJobTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Tracer tracer;

    @Mock
    private Span span;

    @Mock
    private JobExecutionContext jobExecutionContext;

    @Mock
    private JobDetail jobDetail;

    private HttpJob httpJob;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        httpJob = new HttpJob(restTemplate, tracer);

        when(tracer.spanBuilder(anyString())).thenReturn(new SpanBuilder() {

            @Override
            public SpanBuilder setParent(Context context) {
                return this;
            }

            @Override
            public SpanBuilder setNoParent() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'setNoParent'");
            }

            @Override
            public SpanBuilder addLink(SpanContext spanContext) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'addLink'");
            }

            @Override
            public SpanBuilder addLink(SpanContext spanContext, Attributes attributes) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'addLink'");
            }

            @Override
            public SpanBuilder setAttribute(String key, String value) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'setAttribute'");
            }

            @Override
            public SpanBuilder setAttribute(String key, long value) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'setAttribute'");
            }

            @Override
            public SpanBuilder setAttribute(String key, double value) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'setAttribute'");
            }

            @Override
            public SpanBuilder setAttribute(String key, boolean value) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'setAttribute'");
            }

            @Override
            public <T> SpanBuilder setAttribute(AttributeKey<T> key, T value) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'setAttribute'");
            }

            @Override
            public SpanBuilder setSpanKind(SpanKind spanKind) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'setSpanKind'");
            }

            @Override
            public SpanBuilder setStartTimestamp(long startTimestamp, TimeUnit unit) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'setStartTimestamp'");
            }

            @Override
            public Span startSpan() {
                return span;
            }

        });
    }

    @Test
    @DisplayName("Should execute HTTP request successfully with valid configuration")
    void testSuccessfulExecution() {
        // Arrange
        HttpJobDetails details = new HttpJobDetails();
        details.setMethod("POST");
        details.setUrl("http://localhost:8080/test");
        details.setBody("{\"message\":\"hello\"}");

        JobDataMap dataMap = new JobDataMap(Map.of(ApplicationConstant.JOB_DATA_KEY, details));
        when(jobExecutionContext.getJobDetail()).thenReturn(jobDetail);
        when(jobDetail.getJobDataMap()).thenReturn(dataMap);
        when(restTemplate.exchange(eq(details.getUrl()), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok("OK"));

        // Act
        httpJob.execute(jobExecutionContext);

        // Assert
        verify(restTemplate).exchange(eq(details.getUrl()), eq(HttpMethod.POST), any(HttpEntity.class),
                eq(String.class));
        verify(span, atLeastOnce()).end();
    }

    @Test
    @DisplayName("Should throw exception on invalid HTTP method")
    void testExecutionWithInvalidHttpMethod() {
        // Arrange
        HttpJobDetails details = new HttpJobDetails();
        details.setMethod("INVALID");
        details.setUrl("http://localhost:8080/test");
        details.setBody("test");

        JobDataMap dataMap = new JobDataMap(Map.of(ApplicationConstant.JOB_DATA_KEY, details));
        when(jobExecutionContext.getJobDetail()).thenReturn(jobDetail);
        when(jobDetail.getJobDataMap()).thenReturn(dataMap);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> httpJob.execute(jobExecutionContext));
        verify(span, atLeastOnce()).end();
    }
}
