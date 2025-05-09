package hu.evocelot.job;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
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

import hu.evocelot.tickr.constant.ApplicationConstant;
import hu.evocelot.tickr.job.kafka.KafkaProducerJob;
import hu.evocelot.tickr.job.kafka.KafkaProducerJobDetails;
import hu.evocelot.tickr.kafka.KafkaMessageProducer;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;

class KafkaProducerJobTest {

    @Mock
    private Tracer tracer;

    @Mock
    private Span span;

    @Mock
    private KafkaMessageProducer kafkaMessageProducer;

    @Mock
    private JobExecutionContext context;

    @Mock
    private JobDetail jobDetail;

    private KafkaProducerJob jobWithProducer;
    private KafkaProducerJob jobWithoutProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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

        jobWithProducer = new KafkaProducerJob(tracer, kafkaMessageProducer);
        jobWithoutProducer = new KafkaProducerJob(tracer, null);
    }

    @Test
    @DisplayName("Should send Kafka message when producer is available")
    void testExecuteWithProducer() {
        KafkaProducerJobDetails details = new KafkaProducerJobDetails();
        details.setTopic("test-topic");
        details.setMessage("test-message");

        JobDataMap dataMap = new JobDataMap(Map.of(ApplicationConstant.JOB_DATA_KEY, details));
        when(context.getJobDetail()).thenReturn(jobDetail);
        when(jobDetail.getJobDataMap()).thenReturn(dataMap);

        jobWithProducer.execute(context);

        verify(kafkaMessageProducer).sendMessage("test-topic", "test-message");
        verify(span).end();
    }

    @Test
    @DisplayName("Should skip sending message when producer is not available")
    void testExecuteWithoutProducer() {
        KafkaProducerJobDetails details = new KafkaProducerJobDetails();
        details.setTopic("test-topic");
        details.setMessage("test-message");

        JobDataMap dataMap = new JobDataMap(Map.of(ApplicationConstant.JOB_DATA_KEY, details));
        when(context.getJobDetail()).thenReturn(jobDetail);
        when(jobDetail.getJobDataMap()).thenReturn(dataMap);

        jobWithoutProducer.execute(context);

        // No interaction with KafkaMessageProducer
        verifyNoInteractions(kafkaMessageProducer);
        verify(span).end();
    }
}
