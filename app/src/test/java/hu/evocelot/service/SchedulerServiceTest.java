package hu.evocelot.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import hu.evocelot.tickr.configuration.SchedulerConfig;
import hu.evocelot.tickr.job.JobDetails;
import hu.evocelot.tickr.job.custom.CustomJobDetails;
import hu.evocelot.tickr.job.http.HttpJobDetails;
import hu.evocelot.tickr.job.kafka.KafkaProducerJobDetails;
import hu.evocelot.tickr.service.SchedulerService;

class SchedulerServiceTest {

    @Mock
    private Scheduler scheduler;

    @Mock
    private SchedulerConfig schedulerConfig;

    private SchedulerService schedulerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        schedulerService = new SchedulerService(scheduler, schedulerConfig);
    }

    @Test
    @DisplayName("Should schedule HTTP job successfully")
    void testScheduleHttpJob() throws SchedulerException {
        HttpJobDetails httpDetails = new HttpJobDetails();
        JobDetails jobDetails = new JobDetails();
        jobDetails.setName("httpJob");
        jobDetails.setCron("0/5 * * * * ?");
        jobDetails.setHttp(httpDetails);

        when(schedulerConfig.getTasks()).thenReturn(List.of(jobDetails));

        schedulerService.scheduleTasks();

        verify(scheduler, times(1)).scheduleJob(any(JobDetail.class), any(Trigger.class));
    }

    @Test
    @DisplayName("Should schedule Custom job successfully")
    void testScheduleCustomJob() throws SchedulerException {
        CustomJobDetails customDetails = new CustomJobDetails();
        JobDetails jobDetails = new JobDetails();
        jobDetails.setName("customJob");
        jobDetails.setCron("0 0 * * * ?");
        jobDetails.setCustom(customDetails);

        when(schedulerConfig.getTasks()).thenReturn(List.of(jobDetails));

        schedulerService.scheduleTasks();

        verify(scheduler, times(1)).scheduleJob(any(JobDetail.class), any(Trigger.class));
    }

    @Test
    @DisplayName("Should schedule Kafka producer job successfully")
    void testScheduleKafkaProducerJob() throws SchedulerException {
        KafkaProducerJobDetails kafkaDetails = new KafkaProducerJobDetails();
        JobDetails jobDetails = new JobDetails();
        jobDetails.setName("kafkaJob");
        jobDetails.setCron("0 15 10 * * ?");
        jobDetails.setKafkaProducer(kafkaDetails);

        when(schedulerConfig.getTasks()).thenReturn(List.of(jobDetails));

        schedulerService.scheduleTasks();

        verify(scheduler, times(1)).scheduleJob(any(JobDetail.class), any(Trigger.class));
    }

    @Test
    @DisplayName("Should throw exception when job type is unsupported")
    void testScheduleUnsupportedJobType() throws Exception {
        JobDetails jobDetails = new JobDetails();
        jobDetails.setName("invalidJob");
        jobDetails.setCron("0 0/1 * 1/1 * ?");

        when(schedulerConfig.getTasks()).thenReturn(List.of(jobDetails));

        assertThrows(UnsupportedOperationException.class, () -> schedulerService.scheduleTasks());

        verify(scheduler, never()).scheduleJob(any(), any());
    }
}